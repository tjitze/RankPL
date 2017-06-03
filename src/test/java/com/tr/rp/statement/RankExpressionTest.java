package com.tr.rp.statement;

import static com.tr.rp.expressions.Expressions.*;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.Not;
import com.tr.rp.expressions.RankExpr;

public class RankExpressionTest extends RPLBaseTest {

	public void testSimpleRankExpressions() throws RPLException {

		DStatement s = new Assign("c", new RankExpr(eq(var("a"), lit(1))));
		RankedIterator<VarStore> result = s.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(0, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(0, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(0, result.getItem().getIntValue("c"));
		assertEquals(false, result.next());

		s = new Assign("c", new RankExpr(new Not(eq(var("a"), lit(1)))));
		result = s.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(false, result.next());

		s = new Assign("c", new RankExpr(eq(var("a"), lit(2))));
		result = s.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("c"));
		assertEquals(false, result.next());

		s = new Assign("c", new RankExpr(eq(var("a"), lit(3))));
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
