package com.tr.rp.core.rankediterators;

import com.tr.rp.core.VarStore;

public class AbsurdIterator<V> implements RankedIterator<V> {

	@Override
	public boolean next() {
		return false;
	}

	@Override
	public V getItem() {
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
