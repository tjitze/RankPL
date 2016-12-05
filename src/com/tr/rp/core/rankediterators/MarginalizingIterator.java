package com.tr.rp.core.rankediterators;

import java.util.List;

import com.tr.rp.core.VarStore;

/**
 * A ranked iterator that takes another ranked iterator as input,
 * and yields a marginalization of this iterator to a given set of 
 * variables.
 */
public class MarginalizingIterator extends DuplicateRemovingIterator {
	
	public MarginalizingIterator(final RankedIterator in, final List<String> vars) {
		super(new RankedIterator() {
			@Override
			public boolean next() {
				return in.next();
			}

			@Override
			public VarStore getVarStore() {
				return in.getVarStore().marginalize(vars);
			}

			@Override
			public int getRank() {
				return in.getRank();
			}
		});
	}
}
