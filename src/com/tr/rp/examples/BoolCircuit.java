package com.tr.rp.examples;

import java.util.ArrayList;
import java.util.List;

import static com.tr.rp.expressions.Expressions.*;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.MarginalizingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.Equals;
import com.tr.rp.expressions.Expressions;
import com.tr.rp.expressions.Variable;
import com.tr.rp.expressions.Literal;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.RankedChoice;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.tools.ResultPrinter;

/**
 * The circuit diagnosis example from the paper.
 */
public class BoolCircuit {

	public static final boolean F = false;
	public static final boolean T = true;
	
	public static DStatement getProgram() {
		/* IF (fx1 == 0) THEN l1 := (a1 ^ a2) ELSE l1 := 0 << 0 >> 1;
		 * IF (fa1 == 0) THEN l2 := (a1 & a2) ELSE l2 := 0 << 0 >> 1;
		 * IF (fa2 == 0) THEN l3 := (l1 & a3) ELSE l3 := 0 << 0 >> 1;
		 * IF (fx2 == 0) THEN b2 := (l1 ^ a3) ELSE b2 := 0 << 0 >> 1;
		 * IF (fo2 == 0) THEN b1 := (l3 | l2) ELSE b1 := 0 << 0 >> 1;
		 */
		return new ProgramBuilder()
				.add(new RankedChoice(var("fx1"), lit(F), lit(T), lit(1)))
				.add(new RankedChoice(var("fx2"), lit(F), lit(T), lit(1)))
				.add(new RankedChoice(var("fa1"), lit(F), lit(T), lit(1)))
				.add(new RankedChoice(var("fa2"), lit(F), lit(T), lit(1)))
				.add(new RankedChoice(var("fo1"), lit(F), lit(T), lit(1)))
				.add(new RankedChoice(var("a1"),  lit(F), lit(T), lit(0)))
				.add(new RankedChoice(var("a2"),  lit(F), lit(T), lit(0)))
				.add(new RankedChoice(var("a3"),  lit(F), lit(T), lit(0)))
				.add(new IfElse(Expressions.not(new Variable("fx1")),
						new Assign("l1", Expressions.xor(new Variable("a1"), new Variable("a2"))),
						new RankedChoice(var("l1"), lit(F), lit(T), lit(0))))
				.add(new IfElse(Expressions.not(new Variable("fa1")),
						new Assign("l2", Expressions.and(new Variable("a1"), new Variable("a2"))),
						new RankedChoice(var("l2"), lit(F), lit(T), lit(0))))
				.add(new IfElse(Expressions.not(new Variable("fa2")),
						new Assign("l3", Expressions.and(new Variable("l1"), new Variable("a3"))),
						new RankedChoice(var("l3"), lit(F), lit(T), lit(0))))
				.add(new IfElse(Expressions.not(new Variable("fx2")),
						new Assign("b2", Expressions.xor(new Variable("l1"), new Variable("a3"))),
						new RankedChoice(var("b2"), lit(F), lit(T), lit(0))))
				.add(new IfElse(Expressions.not(new Variable("fo1")),
						new Assign("b1", Expressions.or(new Variable("l3"), new Variable("l2"))),
						new RankedChoice(var("b1"), lit(F), lit(T), lit(0))))
				.add(new Observe(getCondition(F,F,T,T,F)))
				.build();
	}
	
	private static Expression getCondition(boolean a1, boolean a2, boolean a3, boolean b1, boolean b2) {
		Expression r = Expressions.and(Expressions.eq(new Variable("a1"), new Literal<Boolean>(a1)), 
				Expressions.eq(new Variable("a2"), new Literal<Boolean>(a2)));
		r = Expressions.and(r, Expressions.eq(new Variable("a3"), new Literal<Boolean>(a3)));
		r = Expressions.and(r, Expressions.eq(new Variable("b1"), new Literal<Boolean>(b1)));
		r = Expressions.and(r, Expressions.eq(new Variable("b2"), new Literal<Boolean>(b2)));
		return r;
	}

	public static void main(String[] args) throws RPLException {

		DStatement circuit = getProgram();
		
		RankedIterator<VarStore> it = circuit.getIterator(new InitialVarStoreIterator());
		List<String> vars = new ArrayList<String>();
		vars.add("fx1"); vars.add("fx2"); vars.add("fa1"); vars.add("fa2"); vars.add("fo1");
//		RankedIterator<VarStore> m = new MarginalizingIterator(it, vars);
		ResultPrinter.print(it, 3);

	}
	
}
