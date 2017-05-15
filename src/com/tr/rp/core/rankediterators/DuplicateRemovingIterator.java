package com.tr.rp.core.rankediterators;

import java.util.HashSet;

import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

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
	public boolean next() throws RPLException {
		boolean hasNext = in.next();
		while (hasNext && seen.contains(in.getItem())) {
			hasNext = in.next();
		}
		if (hasNext) {
			seen.add(in.getItem());
			return true;
		}
		// No more items: we can free up used memory
		seen.clear();
		return false;
	}

	@Override
	public T getItem() throws RPLException {
		return in.getItem();
	}

	@Override
	public int getRank() {
		return in.getRank();
	}

}
