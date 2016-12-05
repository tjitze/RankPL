package com.tr.rp.core.rankediterators;

import com.tr.rp.statement.RPLBaseTest;

public class IteratorSplitterTest extends RPLBaseTest {

	public void testSplitting() {
		IteratorSplitter s = new IteratorSplitter(getTestIterator());
		mustEqual(s.getA(), getTestIterator());
		mustEqual(s.getB(), getTestIterator());
	}
	
	private void mustEqual(RankedIterator a, RankedIterator b) {
		boolean done = false;
		while (!done) {
			assert(a.getRank() == b.getRank());
			assert(a.getVarStore() == b.getVarStore());
			boolean aNext = a.next();
			boolean bNext = b.next();
			assert(aNext == bNext);
			if (!aNext) done = true;
		}		
	}
}
