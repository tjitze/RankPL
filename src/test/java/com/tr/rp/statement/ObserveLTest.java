package com.tr.rp.statement;

import static com.tr.rp.expressions.Expressions.*;

import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;

public class ObserveLTest extends RPLBaseTest {

	
	public void testObserveL() throws RPLException {
		
		ObserveL o = new ObserveL(eq(var("a"), lit(3)), 0);
		RankedIterator<VarStore> result = o.getIterator(this.getTestIterator());

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

		o = new ObserveL(eq(var("a"), lit(3)), 1);
		result = o.getIterator(this.getTestIterator());

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

		o = new ObserveL(eq(var("a"), lit(3)), 2);
		result = o.getIterator(this.getTestIterator());

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

		o = new ObserveL(eq(var("a"), lit(3)), 3);
		result = o.getIterator(this.getTestIterator());

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

		o = new ObserveL(eq(var("a"), lit(3)), 4);
		result = o.getIterator(this.getTestIterator());

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
