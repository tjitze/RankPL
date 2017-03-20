package com.tr.rp.examples;

import java.util.function.Function;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.And;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.bool.LessOrEq;
import com.tr.rp.expressions.bool.LessThan;
import com.tr.rp.expressions.bool.Pred;
import com.tr.rp.expressions.num.Fun;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.Minus;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.expressions.num.Var;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.Choose;
import com.tr.rp.statement.Composition;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.ObserveAlpha;
import com.tr.rp.statement.ObserveShenoy;
import com.tr.rp.statement.Skip;
import com.tr.rp.statement.While;
import com.tr.rp.tools.ResultPrinter;

public class Localizer {


	public static void main(String[] args) {
		
		int k = 4;
		
		int up = 0, down = 1, left = 2, right = 3;

		int[][] map = new int[8][];
		map[7] = new int[] { 1,1,1,1,1,1,1,1,1,1,1 };
		map[6] = new int[] { 0,0,0,0,0,1,1,1,1,1,1 };
		map[5] = new int[] { 0,0,0,0,0,0,0,0,0,0,0 };
		map[4] = new int[] { 0,0,0,0,0,0,0,0,0,0,0 };
		map[3] = new int[] { 0,0,0,0,0,0,0,0,0,0,0 };
		map[2] = new int[] { 1,1,1,1,0,0,1,1,1,1,1 };
		map[1] = new int[] { 1,1,1,1,1,1,1,1,1,1,1 };
		map[0] = new int[] { 1,1,1,1,1,1,1,1,1,1,1 };
		
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 11; x++) {
				if (map[y][x] == 1) {
					System.out.print("\\node[box,fill=gray] at ("+x+","+y+"){};");
				}
			}
		}
		// while (map[5+n][0] = 0) n++;
//		map[5+0][0] = 0
//		map[5+1][0] = 0
//		map[5+2][0] = 1
		//                   1 2 3 4 5
		int[] nd = new int[]{1,1,1,1}; 
		int[] sd = new int[]{2,1,2,3};
		int[] mv = new int[]{right,right,right,right};
		
		DStatement cx = new Choose("x", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 0);
		DStatement cy = new Choose("y", new int[] {0, 1, 2, 3, 4, 5, 6, 7}, 0);
		
		DStatement inner = new ProgramBuilder()
				// Move
				.add(	new IfElse(new And(new LessThan("y", 7), new Equals(new Fun(vs -> mv[vs.getValue("t")]), up)), new Assign("y", new Plus("y", 1)),
						new IfElse(new And(new LessThan(0, "y"), new Equals(new Fun(vs -> mv[vs.getValue("t")]), down)), new Assign("y", new Minus("y", 1)),
						new IfElse(new And(new LessThan(0, "x"), new Equals(new Fun(vs -> mv[vs.getValue("t")]), left)), new Assign("x", new Minus("x", 1)),
						new IfElse(new And(new LessThan("x", 10), new Equals(new Fun(vs -> mv[vs.getValue("t")]), right)), new Assign("x", new Plus("x", 1)))))))
				// North sensor distance 
				.add(new Assign("n", 0))
				.add(new While(new And(
							new Equals(new Fun(vs -> map[vs.getValue("y") + vs.getValue("n")][vs.getValue("x")]), 0),
							new LessThan(new Plus("y", "n"), 7)),
						new Assign("n", new Plus("n", 1))))
				.add(new ObserveAlpha(new Equals("n", new Fun(vs -> nd[vs.getValue("t")] + 1)),1))
				// North sensor distance
				.add(new Assign("s", 0))
				.add(new While(new And(
							new Equals(new Fun(vs -> map[vs.getValue("y") - vs.getValue("s")][vs.getValue("x")]), 0),
							new LessThan(0, new Minus("y", "n"))),
						new Assign("s", new Plus("s", 1))))
				.add(new ObserveAlpha(new Equals("s", new Fun(vs -> sd[vs.getValue("t")] + 1)),1))
				.add(new Assign("t", new Plus("t", 1)))
				.build();
		
		DStatement prog = new ProgramBuilder()
				.add(new Assign("t", 0))
				.add(cx)
				.add(cy)
				.add(new While(new And(new LessThan("t", 3), new LessThan("x", 11)), inner))
				.build();

		VarStore vs = new VarStore();
		RankedIterator it = prog.getIterator(new InitialVarStoreIterator(vs));
		String res = "";
		while (it.next() && it.getRank() == 0) {
			System.out.println(it.getVarStore());
			res += "\\node[box,fill=red] at ("+it.getVarStore().getValue("x")+","+it.getVarStore().getValue("y")+"){};";
		}
		System.out.println(res);

	}

}
