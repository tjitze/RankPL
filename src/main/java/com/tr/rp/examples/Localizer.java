package com.tr.rp.examples;

import static com.tr.rp.expressions.Expressions.*;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.CustomFunction;
import com.tr.rp.expressions.Variable;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.RankedChoice;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.ObserveL;
import com.tr.rp.statement.RangeChoice;
import com.tr.rp.statement.While;

/**
 * The robot localization example from the paper.
 */
public class Localizer {

	public static void main(String[] args) throws RPLException {
				
		int iterations = 3;
		
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

		int[] nd = new int[]{1,1,1,1}; 
		int[] sd = new int[]{2,1,2,3};
		int[] mv = new int[]{right,right,right,right};
		
		DStatement cx = new RangeChoice("x", 0, 10);
		DStatement cy = new RangeChoice("y", 0, 7);
		
		DStatement inner = new ProgramBuilder()
				// Move
				.add(	new IfElse(and(lt(new Variable("y"), lit(7)), eq(CustomFunction.create(vs -> mv[(int)vs.getValue("t")]), lit(up))), new Assign("y", plus(var("y"), lit(1))),
						new IfElse(and(lt(lit(0), new Variable("y")), eq(CustomFunction.create(vs -> mv[(int)vs.getValue("t")]), lit(down))), new Assign("y", minus(var("y"), lit(1))),
						new IfElse(and(lt(lit(0), new Variable("x")), eq(CustomFunction.create(vs -> mv[(int)vs.getValue("t")]), lit(left))), new Assign("x", minus(var("x"), lit(1))),
						new IfElse(and(lt(new Variable("x"), lit(10)), eq(CustomFunction.create(vs -> mv[(int)vs.getValue("t")]), lit(right))), new Assign("x", plus(var("x"), lit(1))))))))
				// North sensor distance 
				.add(new Assign("n", 0))
				.add(new While(and(
						eq(CustomFunction.create(vs -> map[(int)vs.getValue("y") + (int)vs.getValue("n")][(int)vs.getValue("x")]), lit(0)),
						lt(plus(var("y"), var("n")), lit(7))),
						new Assign("n", plus(var("n"), lit(1)))))
				.add(new ObserveL(eq(new Variable("n"), CustomFunction.create(vs -> nd[(int)vs.getValue("t")] + 1)),1))
				// North sensor distance
				.add(new Assign("s", 0))
				.add(new While(and(
						eq(CustomFunction.create(vs -> map[(int)vs.getValue("y") - (int)vs.getValue("s")][(int)vs.getValue("x")]), lit(0)),
						lt(lit(0), minus(var("y"), var("n")))),
						new Assign("s", plus(var("s"), lit(1)))))
				.add(new Observe(eq(var("s"), CustomFunction.create(vs -> sd[(int)vs.getValue("t")] + 1))))//,lit(1)))
				.add(new Assign("t", plus(var("t"), lit(1))))
				.build();
		
		DStatement prog = new ProgramBuilder()
				.add(new Assign("t", 0))
				.add(cx)
				.add(cy)
				.add(new While(and(lt(var("t"), lit(iterations)), lt(var("x"), lit(11))), inner))
				.build();

		RankedIterator<VarStore> it = prog.getIterator(new InitialVarStoreIterator(), null);
		while (it.next() && it.getRank() < 5) {
			System.out.println("Rank: " + it.getRank() + " " + it.getItem());
		}

	}

}
