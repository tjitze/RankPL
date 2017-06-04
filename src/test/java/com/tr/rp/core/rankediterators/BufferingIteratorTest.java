package com.tr.rp.core.rankediterators;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.BufferingIterator;
import com.tr.rp.ranks.Rank;
import com.tr.rp.statement.RPLBaseTest;
import com.tr.rp.varstore.VarStore;

public class BufferingIteratorTest extends RPLBaseTest {

	public void testReset() throws RPLException {
		BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(getTestIterator());

		// Iterate and reset a couple of times.
		// Perform reset at different states (before init, middle, after end)
		for (int i = 0; i < 5; i++) {
			assert(bi.getItem() == null);
			assert(bi.getRank() == Rank.MAX);
			if (i == 0) {
				bi.reset();
				continue;
			}
			assert(bi.next() == true);
			assert(bi.getItem() == v1);
			assert(bi.getRank() == 0);
			if (i == 1) {
				bi.reset();
				continue;
			}
			assert(bi.next() == true);
			assert(bi.getItem() == v2);
			assert(bi.getRank() == 1);
			if (i == 2) {
				bi.reset();
				continue;
			}
			assert(bi.next() == true);
			assert(bi.getItem() == v3);
			assert(bi.getRank() == 2);
			if (i == 3) {
				bi.reset();
				continue;
			}
			assert(bi.next() == false);
			bi.reset();
		}		
	}

	public void testStopBuffering() throws RPLException {

		for (int i = 0; i < 4; i++) {
			BufferingIterator bi = new BufferingIterator(getTestIterator());
	
			assert(bi.getItem() == null);
			assert(bi.getRank() == Rank.MAX);
			if (i == 0) {
				bi.stopBuffering();
			}
			assert(bi.next() == true);
			assert(bi.getItem() == v1);
			assert(bi.getRank() == 0);
			if (i == 1) {
				bi.stopBuffering();
			}
			assert(bi.next() == true);
			assert(bi.getItem() == v2);
			assert(bi.getRank() == 1);
			if (i == 2) {
				bi.stopBuffering();
			}
			assert(bi.next() == true);
			assert(bi.getItem() == v3);
			assert(bi.getRank() == 2);
			if (i == 3) {
				bi.stopBuffering();
			}
			assert(bi.next() == false);
			
			boolean success;
			try {
				bi.reset();
				success = true;
			} catch (IllegalStateException e) {
				success = false;
			}
			assert(success == false);
		}
	}

	public void testIfThenElseScenario() throws RPLException {
		BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(getTestIterator());
		bi.next();
		VarStore v = bi.getItem();
		int rank = bi.getRank();
		bi.stopBuffering();
		assert(bi.getItem() == v);
		assert(bi.getRank() == rank);
	}
	}
