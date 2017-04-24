package com.tr.rp.core.rankediterators;

import com.tr.rp.core.VarStore;

/**
 * A ranked iterator that reproduces its input but ends once
 * a given maximum rank has been reached.
 */
public class RestrictIterator implements RankedIterator<VarStore> {

	public final int maxRank;
	private final RankedIterator<VarStore> in;
	
	public RestrictIterator(RankedIterator<VarStore> in, int maxRank) {
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
	public VarStore getVarStore() {
		return in.getVarStore();
	}

	@Override
	public int getRank() {
		return in.getRank();
	}

}
