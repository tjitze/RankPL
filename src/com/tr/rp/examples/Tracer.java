package com.tr.rp.examples;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Range;
import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.Rank;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.DuplicateRemovingIterator;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.MarginalizingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.And;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.BoolLiteral;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.bool.False;
import com.tr.rp.expressions.bool.LessOrEq;
import com.tr.rp.expressions.bool.LessThan;
import com.tr.rp.expressions.bool.Pred;
import com.tr.rp.expressions.bool.True;
import com.tr.rp.expressions.num.Abs;
import com.tr.rp.expressions.num.Fun;
import com.tr.rp.expressions.num.IndexedVar;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.Minus;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.expressions.num.Var;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.Choose;
import com.tr.rp.statement.Composition;
import com.tr.rp.statement.Cut;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.IndexedAssign;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.ObserveAlpha;
import com.tr.rp.statement.ObserveShenoy;
import com.tr.rp.statement.Skip;
import com.tr.rp.statement.While;
import com.tr.rp.tools.ResultPrinter;

public class Tracer {

	public static class IndexedBoolExp extends BoolExpression {

		private NumExpression indexExpression;
		private BoolExpression[] list;
		
		public IndexedBoolExp(BoolExpression[] list, NumExpression indexExpression) {
			this.list = list;
			this.indexExpression = indexExpression;
		}
		
		@Override
		public boolean isTrue(VarStore e) {
			int index = indexExpression.getVal(e);
			BoolExpression exp = list[index];
			//System.out.println("Checking bool exp " + index + ": " + exp + ", trace = " + getTrace(e));
			if (exp == null) return true;
			return exp.isTrue(e);
		}

		@Override
		public BoolExpression transformRankExpressions(VarStore v, int rank) {
			return this;
		}

		@Override
		public boolean hasDefiniteValue() {
			return false;
		}

		@Override
		public boolean getDefiniteValue() {
			return false;
		}

		@Override
		public boolean hasRankExpression() {
			return false;
		}
	}

	public static BoolExpression is(int x1, int y1) {
		return new And(new Equals(x1, "x"), new Equals(y1, "y"));
	}
	
	public static BoolExpression inside(int x1, int y1, int x2, int y2) {
		// x1 <= x, y1 <= y, x <= x2, y <= y2
		return new And(new And(new LessOrEq(x1, "x"), new LessOrEq(y1, "y")),
			new And(new LessOrEq("x", x2), new LessOrEq("y", y2)));
	}
	
	public static void main(String[] args) {
		
		int maxRank = 1000;
		
		int[] inputx = new int[12];
		int[] inputy = new int[12];

		// Observations
		inputx[0] = 1;	inputy[0] = 1;
		inputx[5] = 3;	inputy[5] = 4;
		inputx[8] = 5;	inputy[8] = 5;
		inputx[9] = 6;	inputy[9] = 3;
		inputx[11] = 6;	inputy[11] = 1;
		
		
		DStatement observe = new IfElse(
				new Pred(vs -> inputx[vs.getValue("t")] != 0 && inputy[vs.getValue("t")] != 0),
				new ObserveAlpha(new Pred(vs -> 
					inputx[vs.getValue("t")] == vs.getValue("x") && 
					inputy[vs.getValue("t")] == vs.getValue("y")),2));

		int up = 0, down = 1, left = 2, right = 3;

		DStatement inner = new ProgramBuilder()
				.add(new IfElse(new Equals("h", up),
						new Composition(
								new Assign("y", new Plus(new IndexedVar("y", "t"), 1)),
								new Assign("x", new IndexedVar("x", "t")),
								new Choose("h", new int[] {up, left, right}, new int[] {1, 0})),
					new IfElse(new Equals("h", down),
						new Composition(
								new Assign("y", new Minus(new IndexedVar("y", "t"), 1)),
								new Assign("x", new IndexedVar("x", "t")),
								new Choose("h", new int[] {down, left, right}, new int[] {1, 0})),
					new IfElse(new Equals("h", left),
						new Composition(
								new Assign("x", new Minus(new IndexedVar("x", "t"), 1)),
								new Assign("y", new IndexedVar("y", "t")),
								new Choose("h", new int[] {left, up, down}, new int[] {1, 0})),
					new IfElse(new Equals("h", right),
						new Composition(
								new Assign("x", new Plus(new IndexedVar("x", "t"), 1)),
								new Assign("y", new IndexedVar("y", "t")),
								new Choose("h", new int[] {right, up, down}, new int[] {1, 0})),
					new Skip())))))
				.add(new Observe(new LessOrEq("x", 7)))
				.add(new Observe(new LessOrEq("y", 7)))
				.add(new Observe(new LessOrEq(1, "x")))
				.add(new Observe(new LessOrEq(1, "y")))
				.add(new Assign("t", new Plus("t", 1)))
				.add(new IndexedAssign("x", new Var("t"), new Var("x")))
				.add(new IndexedAssign("y", new Var("t"), new Var("y")))
				.add(observe)
				.build();

		Function<Integer, DStatement> pr = k -> new ProgramBuilder()
			.add(new IndexedAssign("x", new IntLiteral(0), new IntLiteral(1)))
			.add(new IndexedAssign("y", new IntLiteral(0), new IntLiteral(1)))
			.add(new Assign("t", 0))
			.add(new Assign("h", up))
			.add(new While(new LessThan("t", k), inner))
			.build();

		VarStore vs = new VarStore();
		vs.initializeArray("x", 12);
		vs.initializeArray("y", 12);
		RankedIterator it = pr.apply(9).getIterator(new InitialVarStoreIterator(vs));
		printTrace(it,2);
	}

	private static void printTrace(RankedIterator it, int i) {
		while (it.next() && it.getRank() <= i) {
			int rank = it.getRank();
			String trace = String.join(" -- ", getTrace(it.getVarStore()).stream().map(k -> k.toString()).collect(Collectors.toList()));
			System.out.println("\\draw["+(rank==0?"red":(rank==1?"blue":"green"))+",thick] " + trace +";");
		}
		
	}
	public static List<Pnt> getTrace(VarStore v) {
		int[] x = v.get1DArray("x", 12);
		int[] y = v.get1DArray("y", 12);
		if (x.length != y.length) throw new IllegalArgumentException();
		List<Pnt> coords = new ArrayList<Pnt>();
		for (int p = 0; p < x.length; p++) coords.add(new Pnt(x[p],y[p]));
		return coords;
	}
	public static String prettyPrintTrace(VarStore v) {
		int width = 10, height = 10;
		char[][] matrix = new char[height][];
		for (int y = 0; y < height; y++) {
			matrix[y] = new char[width];
			for (int x = 0; x < width; x++) {
				matrix[y][x] = '.';
			}
		}
		List<Pnt> points = getTrace(v);
		for (int i = 1; i < points.size(); i++) {
			Pnt p1 = points.get(i-1);
			Pnt p2 = points.get(i);
			// Same x?
			if (p1.x == p2.x) {
				int y1, y2;
				if (p1.y > p2.y) {
					y1 = p2.y;
					y2 = p1.y;
				} else {
					y1 = p1.y;
					y2 = p2.y;
				}
				for (int y = y1; y <= y2; y++) {
					if (y >= 0 && p1.x >= 0 && y < height && p1.x < width) {
						matrix[y][p1.x] = 'x';
					}
				}
			}
			// Same y
			else if (p1.y == p2.y) {
				int x1, x2;
				if (p1.x > p2.x) {
					x1 = p2.x;
					x2 = p1.x;
				} else {
					x1 = p1.x;
					x2 = p2.x;
				}
				for (int x = x1; x <= x2; x++) {
					if (p1.y >= 0 && x >= 0 && p1.y < height && x < width) {
						matrix[p1.y][x] = 'x';
					}
				}
			} else {
				System.err.println("Error");
			}
//			matrix[p1.y][p1.x] = '+';
//			matrix[p2.y][p2.x] = '+';
		}
		String out = "";
		for (int y = 0; y < height; y++) {
			String line = "";
			for (int x = 0; x < width; x++) {
				line += matrix[y][x];
			}
			out += line + "\n";
		}
		return out;
	}
		
	
	public static class Pnt extends Point {
		public Pnt(int x, int y) {
			super(x, y);
		}
		public String toString() { return "("+(int)this.getX()+","+(int)this.getY()+")"; }
	}

}
