package com.tr.rp.statement;

import static com.tr.rp.ast.expressions.Expressions.*;
import static com.tr.rp.ast.statements.Statements.*;

import com.tr.rp.ast.statements.ObserveL;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

public class ObserveLTest extends RPLBaseTest {

	
	public void testObserveL() throws RPLException {
		
		ObserveL o = observeL(eq(var("a"), lit(3)), 0);
		RankedIterator<VarStore> result = o.getIterator(this.getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(0, result.getRank());
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(1, result.getRank());
		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(2, result.getRank());
		assertEquals(false, result.next());

		o = observeL(eq(var("a"), lit(3)), 1);
		result = o.getIterator(this.getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(0, result.getRank());
		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(1, result.getRank());
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(1, result.getRank());
		assertEquals(false, result.next());

		o = observeL(eq(var("a"), lit(3)), 2);
		result = o.getIterator(this.getTestIterator(), ExecutionContext.createDefault());

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

		o = observeL(eq(var("a"), lit(3)), 3);
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

		o = observeL(eq(var("a"), lit(3)), 4);
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
