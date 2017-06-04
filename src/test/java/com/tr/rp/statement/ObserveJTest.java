package com.tr.rp.statement;

import static com.tr.rp.ast.expressions.Expressions.*;

import com.tr.rp.ast.statements.ObserveJ;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

public class ObserveJTest extends RPLBaseTest {

	public void testObserveJ() throws RPLException {
		
		ObserveJ o = new ObserveJ(eq(var("a"), lit(3)), 0);
		RankedIterator<VarStore> result = o.getIterator(this.getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(0, result.getRank());
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(0, result.getRank());
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(1, result.getRank());
		assertEquals(false, result.next());

		o = new ObserveJ(eq(var("a"), lit(3)), 1);
		result = o.getIterator(this.getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(0, result.getRank());
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(1, result.getRank());
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(2, result.getRank());
		assertEquals(false, result.next());

		o = new ObserveJ(eq(var("a"), lit(3)), 2);
		result = o.getIterator(this.getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(0, result.getRank());
		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(2, result.getRank());
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(3, result.getRank());
		assertEquals(false, result.next());

	}
}
