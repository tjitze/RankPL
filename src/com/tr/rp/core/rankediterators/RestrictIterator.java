package com.tr.rp.core.rankediterators;

import com.tr.rp.exceptions.RPLException;

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
	public boolean next() throws RPLException {
		boolean next = in.next();
		if (next && in.getRank() < maxRank) {
			return true;
		}
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
