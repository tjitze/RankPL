package com.tr.rp.core.rankediterators;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.IteratorSplitter;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.statement.RPLBaseTest;

public class IteratorSplitterTest extends RPLBaseTest {

	public void testSplitting() throws RPLException {
		IteratorSplitter s = new IteratorSplitter(getTestIterator());
		mustEqual(s.getA(), getTestIterator());
		mustEqual(s.getB(), getTestIterator());
	}
	
	private void mustEqual(RankedIterator a, RankedIterator b) throws RPLException {
		boolean done = false;
		while (!done) {
			assert(a.getRank() == b.getRank());
			assert(a.getItem() == b.getItem());
			boolean aNext = a.next();
			boolean bNext = b.next();
			assert(aNext == bNext);
			if (!aNext) done = true;
		}		
	}
}
