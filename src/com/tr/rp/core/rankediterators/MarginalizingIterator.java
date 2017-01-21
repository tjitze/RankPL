package com.tr.rp.core.rankediterators;

import java.util.Arrays;
import java.util.List;

import com.tr.rp.core.VarStore;

/**
 * A ranked iterator that takes another ranked iterator as input,
 * and yields a marginalization of this iterator to a given set of 
 * variables.
 */
public class MarginalizingIterator extends DuplicateRemovingIterator {
	
	public MarginalizingIterator(RankedIterator in, String ... vars) {
		this(in, Arrays.asList(vars));
	}
	
	public MarginalizingIterator(final RankedIterator in, final List<String> vars) {
		super(new RankedIterator() {
			@Override
			public boolean next() {
				return in.next();
			}

			@Override
			public VarStore getVarStore() {
				if (in == null) return null;
				VarStore v = in.getVarStore();
				if (v == null) return null;
				return v.marginalize(vars);
			}

			@Override
			public int getRank() {
				return in == null? 0: in.getRank();
			}
		});
	}
}
