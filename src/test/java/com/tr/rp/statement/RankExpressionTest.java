package com.tr.rp.statement;

import static com.tr.rp.ast.expressions.Expressions.*;
import static com.tr.rp.ast.statements.Statements.*;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.ast.expressions.RankExpr;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

public class RankExpressionTest extends RPLBaseTest {

	public void testSimpleRankExpressions() throws RPLException {

		AbstractStatement s = assign("c", new RankExpr(eq(var("a"), lit(1))));
		RankedIterator<VarStore> result = s.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(0, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(0, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(0, result.getItem().getIntValue("c"));
		assertEquals(false, result.next());

		s = assign("c", new RankExpr(new Not(eq(var("a"), lit(1)))));
		result = s.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(false, result.next());

		s = assign("c", new RankExpr(eq(var("a"), lit(2))));
		result = s.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(false, result.next());

		s = assign("c", new RankExpr(eq(var("a"), lit(3))));
		result = s.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("c"));
		assertEquals(false, result.next());

	}
}
