package com.tr.rp.core.rankediterators;

import java.util.HashSet;

import com.tr.rp.core.VarStore;

/**
 * Ranked iterator that takes another ranked iterator as input and
 * yields the same items except that duplicates are removed in the
 * order in which they appear. For example, if the input iterator
 * returns a variable store X first with a rank of n and later with
 * a rank of n + m (m >= 0) then the latter will be skipped.
 */
public class DuplicateRemovingIterator<T> implements RankedIterator<T> {

	private final HashSet<T> seen = new HashSet<T>();
	private final RankedIterator<T> in;
	
	public DuplicateRemovingIterator(RankedIterator<T> in) {
		this.in = in;
	}

	@Override
	public boolean next() {
		boolean hasNext = in.next();
		while (hasNext && seen.contains(in.getVarStore())) {
			hasNext = in.next();
		}
		if (hasNext) {
			seen.add(in.getVarStore());
			return true;
		}
		// No more items: we can free up used memory
		seen.clear();
		return false;
	}

	@Override
	public T getVarStore() {
		return in.getVarStore();
	}

	@Override
	public int getRank() {
		return in.getRank();
	}

}
