package com.tr.rp.statement;

import static com.tr.rp.expressions.Expressions.*;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.Literal;

public class WhileTest extends RPLBaseTest {

	public void testWhile() throws RPLException {
		
		Expression c = lt(var("x"), new Literal<Integer>(3));
		DStatement s = new ProgramBuilder()
				.add(new RankedChoice(var("y"), plus(var("y"), var("x")), plus(var("y"), new Literal<Integer>(10)), 1))
				.add(new Assign("x", plus(var("x"), new Literal<Integer>(1))))
				.build();
		DStatement p = new ProgramBuilder()
				.add(new Assign("x", 0))
				.add(new Assign("y", 0))
				.add(new While(c, s))
				.build();
		RankedIterator<VarStore> result = p.getIterator(new InitialVarStoreIterator());
		
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
