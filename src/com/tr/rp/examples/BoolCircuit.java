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
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.Skip;
import com.tr.rp.tools.ResultPrinter;

public class BoolCircuit {

	public static void main(String[] args) {

		BoolExpression c1 = new Equals("a1", 0);
		BoolExpression c2 = new Equals("a2", 1);
		BoolExpression c3 = new Equals("a3", 0);
		BoolExpression c4 = new Equals("b1", 0);
		BoolExpression c5 = new Equals("b2", 0);
		BoolExpression cond1 = new And(c1, c2).and(c3).and(c4).and(c5);
		//0: [a1=0, a2=1, a3=0, b1=0, b2=0, fa1=0, fa2=0, fo1=0, fx1=0, fx2=1, l1=1, l2=0, l3=0]
		//0: [a1=0, a2=1, a3=0, b1=0, b2=0, fa1=0, fa2=0, fo1=0, fx1=1, fx2=0, l1=0, l2=0, l3=0]

		c1 = new Equals("a1", 0);
		c2 = new Equals("a2", 1);
		c3 = new Equals("a3", 1);
		c4 = new Equals("b1", 1);
		c5 = new Equals("b2", 0);
		BoolExpression cond2 = new And(c1, c2).and(c3).and(c4).and(c5);
		//0: [a1=0, a2=1, a3=1, b1=1, b2=0, fa1=0, fa2=0, fo1=0, fx1=1, fx2=0, l1=0, l2=0, l3=0]
		
		DStatement p = new ProgramBuilder()
				.add(new Choose("fx1", 0, 1, 1))
				.add(new Choose("fx2", 0, 1, 1))
				.add(new Choose("fa1", 0, 1, 1))
				.add(new Choose("fa2", 0, 1, 1))
				.add(new Choose("fo1", 0, 1, 1))

				.add(new Choose("a1", 0, 1, 0))
				.add(new Choose("a2", 0, 1, 0))
				.add(new Choose("a3", 0, 1, 0))

				.add(new IfElse(new Equals("fx1", 0),
						new Assign("l1", new NumXor("a1","a2")),
						new Choose("l1", 0, 1)))
				
				.add(new IfElse(new Equals("fa1", 0),
						new Assign("l2", new NumAnd("a1","a2")),
						new Choose("l2", 0, 1)))
				
				.add(new IfElse(new Equals("fa2", 0),
						new Assign("l3", new NumAnd("l1","a3")),
						new Choose("l3", 0, 1)))
				
				.add(new IfElse(new Equals("fx2", 0),
						new Assign("b1", new NumXor("l1","a3")),
						new Choose("b1", 0, 1)))
				
				.add(new IfElse(new Equals("fo1", 0),
						new Assign("b2", new NumOr("l3","l2")),
						new Choose("b2", 0, 1)))

				.add(new IfElse(new Equals("fo1", 0),
						new Assign("b2", new NumOr("l3","l2")),
						new Choose("b2", 0, 1)))

				.add(new Observe(cond2))
				.add(new Observe(cond1))
				.build();

		RankedIterator i = p.getIterator(new InitialVarStoreIterator());
		ResultPrinter.print(i);
	}
}
