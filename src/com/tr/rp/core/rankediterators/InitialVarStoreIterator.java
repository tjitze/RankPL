package com.tr.rp.core.rankediterators;

import com.tr.rp.core.VarStore;

/**
 * A ranked iterator that represents the initial state of program 
 * execution. I.e., it returns just one variable store (the initial
 * variable store assigning 0 to all variables) that is ranked 0, 
 * and nothing else.
 */
public class InitialVarStoreIterator implements RankedIterator<VarStore> {
	
	private VarStore vs;
	private boolean initialized = false;
	
	public InitialVarStoreIterator(VarStore vs) {
		this.vs = vs;
	}
	
	public InitialVarStoreIterator() {
		this(new VarStore());
	}
	
	@Override
	public boolean next() {
		if (!initialized) {
			initialized = true;
			return true;
		} else {
			vs = null;
			return false;
		}
	}

	@Override
	public VarStore getVarStore() {
		return vs;
	}

	@Override
	public int getRank() {
		return 0;
	}
}
