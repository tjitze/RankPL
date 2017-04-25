package com.tr.rp.statement;

import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.Equals;

public class ObserveJTest extends RPLBaseTest {

	
	public void testObserveAlpha() {
		
		ObserveJ o = new ObserveJ(new Equals("a", 3), 0);
		RankedIterator<VarStore> result = o.getIterator(this.getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getRank() == 0);
		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getRank() == 0);
		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getRank() == 1);
		assert(result.next() == false);

		o = new ObserveJ(new Equals("a", 3), 1);
		result = o.getIterator(this.getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getRank() == 0);
		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getRank() == 1);
		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getRank() == 2);
		assert(result.next() == false);

		o = new ObserveJ(new Equals("a", 3), 2);
		result = o.getIterator(this.getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getRank() == 0);
		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getRank() == 2);
		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getRank() == 3);
		assert(result.next() == false);

	}
}
