package com.tr.rp.core.rankediterators;

import java.util.LinkedList;

import com.tr.rp.core.VarStore;

import junit.framework.TestCase;

public class IteratorSplitterTest extends TestCase {

	private static final VarStore v1 = new VarStore();
	private static final VarStore v2 = new VarStore();
	private static final VarStore v3 = new VarStore();

	public void testSplitting() {
		IteratorSplitter<VarStore> s = new IteratorSplitter<VarStore>(getTestIterator());
		mustEqual(s.getA(), getTestIterator());
		mustEqual(s.getB(), getTestIterator());
	}
	
	private void mustEqual(RankedIterator<VarStore> a, RankedIterator<VarStore> b) {
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
