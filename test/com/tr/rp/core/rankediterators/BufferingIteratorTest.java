package com.tr.rp.core.rankediterators;

import com.tr.rp.core.VarStore;
import com.tr.rp.statement.RPLBaseTest;

public class BufferingIteratorTest extends RPLBaseTest {

	public void testReset() {
		BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(getTestIterator());

		// Iterate and reset a couple of times.
		// Perform reset at different states (before init, middle, after end)
		for (int i = 0; i < 5; i++) {
			assert(bi.getVarStore() == null);
			assert(bi.getRank() == Integer.MAX_VALUE);
			if (i == 0) {
				bi.reset();
				continue;
			}
			assert(bi.next() == true);
			assert(bi.getVarStore() == v1);
			assert(bi.getRank() == 0);
			if (i == 1) {
				bi.reset();
				continue;
			}
			assert(bi.next() == true);
			assert(bi.getVarStore() == v2);
			assert(bi.getRank() == 1);
			if (i == 2) {
				bi.reset();
				continue;
			}
			assert(bi.next() == true);
			assert(bi.getVarStore() == v3);
			assert(bi.getRank() == 2);
			if (i == 3) {
				bi.reset();
				continue;
			}
			assert(bi.next() == false);
			bi.reset();
		}		
	}

	public void testStopBuffering() {

		for (int i = 0; i < 4; i++) {
			BufferingIterator bi = new BufferingIterator(getTestIterator());
	
			assert(bi.getVarStore() == null);
			assert(bi.getRank() == Integer.MAX_VALUE);
			if (i == 0) {
				bi.stopBuffering();
			}
			assert(bi.next() == true);
			assert(bi.getVarStore() == v1);
			assert(bi.getRank() == 0);
			if (i == 1) {
				bi.stopBuffering();
			}
			assert(bi.next() == true);
			assert(bi.getVarStore() == v2);
			assert(bi.getRank() == 1);
			if (i == 2) {
				bi.stopBuffering();
			}
			assert(bi.next() == true);
			assert(bi.getVarStore() == v3);
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

	public void testIfThenElseScenario() {
		BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(getTestIterator());
		bi.next();
		VarStore v = bi.getVarStore();
		int rank = bi.getRank();
		bi.stopBuffering();
		assert(bi.getVarStore() == v);
		assert(bi.getRank() == rank);
	}
	}
