package com.tr.rp.examples;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.And;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.BoolLiteral;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.num.NumAnd;
import com.tr.rp.expressions.num.NumOr;
import com.tr.rp.expressions.num.NumXor;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.Choose;
import com.tr.rp.statement.Composition;
import com.tr.rp.statement.Observe;

public class BoolCircuit {

	public static void main(String[] args) {
		
		BoolExpression c1 = new Equals("in1", 1);
		BoolExpression c2 = new Equals("in2", 0);
		BoolExpression c3 = new Equals("in3", 1);
		BoolExpression c4 = new Equals("out1", 1);
		BoolExpression c5 = new Equals("out2", 0);
		BoolExpression cond = new And(c1, c2).and(c3).and(c4).and(c5);
		
		DStatement p = new ProgramBuilder()
				.add(new Choose("in1", 0, 1))
				.add(new Choose("in2", 0, 1))
				.add(new Choose("in3", 0, 1))
				.add(new Choose(new Assign("l1", new NumXor("in1","in2")),
						new Composition(
								new Assign("fail_x1", 1),
								new Choose("l1", 0, 1)), 2))
//				.add(new Choose(new Assign("l2", new NumAnd("in1","in2")),
//						new Composition(
//								new Assign("fail_a1", 1),
//								new Choose("l2", 0, 1)), 2))
//				.add(new Choose(new Assign("l3", new NumAnd("l1","in3")),
//						new Composition(
//								new Assign("fail_a2", 1),
//								new Choose("l3", 0, 1)), 2))
//				.add(new Choose(new Assign("out1", new NumXor("l1","in3")),
//						new Composition(
//								new Assign("fail_x2", 1),
//								new Choose("out1", 0, 1)), 2))
//				.add(new Choose(new Assign("out2", new NumOr("l3","l2")),
//						new Composition(
//								new Assign("fail_o1", 1),
//								new Choose("out2", 0, 1)), 2))
//				.add(new Observe(cond))
				.build();

		System.out.println("Iterator:");
		RankedIterator<VarStore> i = p.getIterator(new InitialVarStoreIterator());
		while (i.next()) {
			System.out.println("Ranked " + i.getRank() + ": " + i.getItem());
		}
	}
}
