package com.tr.rp.statement;

import static com.tr.rp.ast.expressions.Expressions.*;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.ProgramBuilder;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.statements.Assign;
import com.tr.rp.ast.statements.RankedChoice;
import com.tr.rp.ast.statements.While;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.InitialVarStoreIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

public class WhileTest extends RPLBaseTest {

	public void testWhile() throws RPLException {
		
		AbstractExpression c = lt(var("x"), new Literal<Integer>(3));
		AbstractStatement s = new ProgramBuilder()
				.add(new RankedChoice(target("y"), plus(var("y"), var("x")), plus(var("y"), new Literal<Integer>(10)), lit(1)))
				.add(new Assign("x", plus(var("x"), new Literal<Integer>(1))))
				.build();
		AbstractStatement p = new ProgramBuilder()
				.add(new Assign("x", 0))
				.add(new Assign("y", 0))
				.add(new While(c, s))
				.build();
		RankedIterator<VarStore> result = p.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("y"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("y"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("y"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("y"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(21, result.getItem().getIntValue("y"));
		assertEquals(2, result.getRank());

		assertEquals(true, result.next());
		assertEquals(20, result.getItem().getIntValue("y"));
		assertEquals(2, result.getRank());

		assertEquals(true, result.next());
		assertEquals(22, result.getItem().getIntValue("y"));
		assertEquals(2, result.getRank());

		assertEquals(true, result.next());
		assertEquals(30, result.getItem().getIntValue("y"));
		assertEquals(3, result.getRank());

		assertEquals(false, result.next());
	}
}
