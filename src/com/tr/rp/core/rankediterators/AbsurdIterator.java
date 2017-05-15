package com.tr.rp.core.rankediterators;

import com.tr.rp.exceptions.RPLException;

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
		return Integer.MAX_VALUE;
	}

	public String toString() {
		return "AbsurdIterator";
	}
}
