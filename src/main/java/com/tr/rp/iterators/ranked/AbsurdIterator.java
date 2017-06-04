package com.tr.rp.iterators.ranked;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.Rank;

/**
 * A ranked iterator that contains no items. Represents absurdity.
 */
public class AbsurdIterator<T> implements RankedIterator<T> {

	@Override
	public boolean next() throws RPLException {
		return false;
	}

	@Override
	public T getItem() throws RPLException {
		return null;
	}

	@Override
	public int getRank() {
		return Rank.MAX;
	}

	public String toString() {
		return "AbsurdIterator";
	}
}
