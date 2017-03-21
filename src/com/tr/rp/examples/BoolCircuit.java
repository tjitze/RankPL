package com.tr.rp.examples;

import java.util.ArrayList;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.MarginalizingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.And;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.num.NumAnd;
import com.tr.rp.expressions.num.NumOr;
import com.tr.rp.expressions.num.NumXor;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.Choose;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.tools.ResultPrinter;

public class BoolCircuit {

	public static void main(String[] args) {
		String program = 
			"{ a1 := 0 } << 0 >> { a1 := 1 };" +
			"{ a2 := 0 } << 0 >> { a2 := 1 };" +
			"{ a3 := 0 } << 0 >> { a3 := 1 };" +
			"{ fx1 := 0 } << 0 >> { fx1 := 1 };" +
			"{ fx2 := 0 } << 0 >> { fx2 := 1 };" +
			"{ fa1 := 0 } << 0 >> { fa1 := 1 };" +
			"{ fa2 := 0 } << 0 >> { fa2 := 1 };" +
			"{ fo1 := 0 } << 0 >> { fo1 := 1 };" +
			"IF (fx1 == 0) THEN (l1 := "
		DStatement circuit = new ProgramBuilder()
				.add(new Choose("a1", 0, 1, 0))
				.add(new Choose("a2", 0, 1, 0))
				.add(new Choose("a3", 0, 1, 0))
				.add(new IfElse(new Equals("fx1", 0),
						new Assign("l1", new NumXor("a1","a2")),
						new Choose("l1", 0, 1, 0)))
				.add(new IfElse(new Equals("fa1", 0),
						new Assign("l2", new NumAnd("a1","a2")),
						new Choose("l2", 0, 1, 0)))
				.add(new IfElse(new Equals("fa2", 0),
						new Assign("l3", new NumAnd("l1","a3")),
						new Choose("l3", 0, 1, 0)))
				.add(new IfElse(new Equals("fx2", 0),
						new Assign("b2", new NumXor("l1","a3")),
						new Choose("b2", 0, 1, 0)))
				.add(new IfElse(new Equals("fo1", 0),
						new Assign("b1", new NumOr("l3","l2")),
						new Choose("b1", 0, 1, 0)))
				.build();
				
		DStatement p = new ProgramBuilder()
				.add(new Choose("fx1", 0, 1, 1))
				.add(new Choose("fx2", 0, 1, 1))
				.add(new Choose("fa1", 0, 1, 1))
				.add(new Choose("fa2", 0, 1, 1))
				.add(new Choose("fo1", 0, 1, 1))
				.add(circuit)
				.add(new Observe(getCondition(0,0,1,1,0)))
				.build();

		RankedIterator it = p.getIterator(new InitialVarStoreIterator());
		List<String> vars = new ArrayList<String>();
		vars.add("fx1"); vars.add("fx2"); vars.add("fa1"); vars.add("fa2"); vars.add("fo1");
		RankedIterator m = new MarginalizingIterator(it, vars);
		ResultPrinter.print(m, 3);

	}

	private static BoolExpression getCondition(int a1, int a2, int a3, int b1, int b2) {
		And r = new And(new Equals("a1", a1), new Equals("a2", a2));
		r = new And(r, new Equals("a3", a3));
		r = new And(r, new Equals("b1", b1));
		r = new And(r, new Equals("b2", b2));
		return r;
	}
	
}
