package com.tr.rp.core.rankediterators;

import java.util.List;

import com.tr.rp.core.VarStore;

public class MarginalizingIterator extends DuplicateRemovingIterator {
	
	public MarginalizingIterator(final RankedIterator in, final List<String> vars) {
		super(new RankedIterator() {
			@Override
			public boolean next() {
				return in.next();
			}

			@Override
			public VarStore getItem() {
				return in.getItem().marginalize(vars);
			}

			@Override
			public int getRank() {
				return in.getRank();
			}
		});
	}
}
