package com.tr.rp.statement;

import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.tools.ResultPrinter;

public class ObserveAlphaTest extends RPLBaseTest {

	
	public void testObserveAlpha() {
		
		ObserveAlpha o = new ObserveAlpha(new Equals("a", 3), 0);
		RankedIterator result = o.getIterator(this.getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 3);
		assert(result.getRank() == 0);
		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 1);
		assert(result.getRank() == 0);
		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 2);
		assert(result.getRank() == 1);
		assert(result.next() == false);

		o = new ObserveAlpha(new Equals("a", 3), 1);
		result = o.getIterator(this.getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 3);
		assert(result.getRank() == 0);
		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 1);
		assert(result.getRank() == 1);
		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 2);
		assert(result.getRank() == 2);
		assert(result.next() == false);

		o = new ObserveAlpha(new Equals("a", 3), 2);
		result = o.getIterator(this.getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 3);
		assert(result.getRank() == 0);
		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 1);
		assert(result.getRank() == 2);
		assert(result.next() == true);
		assert(result.getVarStore().getValue("a") == 2);
		assert(result.getRank() == 3);
		assert(result.next() == false);

	}
}
