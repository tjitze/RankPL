package com.tr.rp.core.rankediterators;

import com.tr.rp.core.VarStore;

/**
 * A ranked iterator that contains no items. Represents absurdity.
 */
public class AbsurdIterator implements RankedIterator {

	@Override
	public boolean next() {
		return false;
	}

	@Override
	public VarStore getVarStore() {
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
