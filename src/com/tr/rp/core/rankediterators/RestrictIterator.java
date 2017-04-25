package com.tr.rp.core.rankediterators;

import com.tr.rp.core.VarStore;

/**
 * A ranked iterator that reproduces its input but ends once
 * a given maximum rank has been reached.
 */
public class RestrictIterator<T> implements RankedIterator<T> {

	public final int maxRank;
	private final RankedIterator<T> in;
	
	public RestrictIterator(RankedIterator<T> in, int maxRank) {
		this.in = in;
		this.maxRank = maxRank;
	}
	
	@Override
	public boolean next() {
		boolean next = in.next();
		if (next && in.getRank() < maxRank) {
			return true;
		}
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
