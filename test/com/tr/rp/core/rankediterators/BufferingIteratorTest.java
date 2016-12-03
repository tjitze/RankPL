package com.tr.rp.core.rankediterators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tr.rp.core.VarStore;

import junit.framework.TestCase;

public class BufferingIteratorTest extends TestCase {

	private static final VarStore v1 = new VarStore();
	private static final VarStore v2 = new VarStore();
	private static final VarStore v3 = new VarStore();
	
	public void testReset() {
		BufferingIterator bi = new BufferingIterator(getTestIterator());

		// Iterate and reset a couple of times.
		// Perform reset at different states (before init, middle, after end)
		for (int i = 0; i < 5; i++) {
			assert(bi.getItem() == null);
			assert(bi.getRank() == Integer.MAX_VALUE);
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

	public void testStopBuffering() {

		for (int i = 0; i < 4; i++) {
			BufferingIterator bi = new BufferingIterator(getTestIterator());
	
			assert(bi.getItem() == null);
			assert(bi.getRank() == Integer.MAX_VALUE);
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

	public void testIfThenElseScenario() {
		BufferingIterator bi = new BufferingIterator(getTestIterator());
		bi.next();
		VarStore v = bi.getItem();
		int rank = bi.getRank();
		bi.stopBuffering();
		assert(bi.getItem() == v);
		assert(bi.getRank() == rank);
	}
	
	private RankedIterator<VarStore> getTestIterator() {
		final LinkedList<VarStore> list = new LinkedList<VarStore>() {{
			addLast(v1);
			addLast(v2);
			addLast(v3);
		}};
		return new RankedIterator<VarStore>() {

			private int c = -1;
			
			@Override
			public boolean next() {
				c++;
				return c < list.size();
			}

			@Override
			public VarStore getItem() {
				if (c < 0 || c >= list.size()) return null;
				return list.get(c);
			}

			@Override
			public int getRank() {
				return c;
			}
		};
	}
}
