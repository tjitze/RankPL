package com.tr.rp.core.rankediterators;

/**
 * A ranked iterator that contains no items. Represents absurdity.
 */
public class AbsurdIterator<T> implements RankedIterator<T> {

	@Override
	public boolean next() {
		return false;
	}

	@Override
	public T getVarStore() {
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
