package com.tr.rp.core.rankediterators;

import java.util.LinkedList;

import com.tr.rp.core.VarStore;

public class MergingIteratorTest {

	public void testMerge() {
		RankedIterator<VarStore> ri1 = getTestIterator(0, 1, 2, 3);
		RankedIterator<VarStore> ri2 = getTestIterator(0, 1, 2, 3);
		IteratorSplitter<VarStore> s1 = new IteratorSplitter<VarStore>(ri1);
		IteratorSplitter<VarStore> s2 = new IteratorSplitter<VarStore>(ri2);
		MergingIterator<VarStore> mi = new MergingIterator(s1.getA(), s1.getB());
		
	}
	
	private RankedIterator<VarStore> getTestIterator(int...ranks) {
		final LinkedList<VarStore> list = new LinkedList<VarStore>();
		for (int rank: ranks) {
			list.add(new VarStore());
		}
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
				return ranks[c];
			}
		};
	}

	private RankedIterator<VarStore> getTestIterator2() {
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
