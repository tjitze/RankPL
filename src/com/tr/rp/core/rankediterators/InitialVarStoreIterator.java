package com.tr.rp.core.rankediterators;

import com.tr.rp.core.VarStore;

public class InitialVarStoreIterator implements RankedIterator {
	
	private VarStore v = new VarStore();
	private boolean initialized = false;
	@Override
	public boolean next() {
		if (!initialized) {
			initialized = true;
			return true;
		} else {
			v = null;
			return false;
		}
	}

	@Override
	public VarStore getItem() {
		return v;
	}

	@Override
	public int getRank() {
		return 0;
	}
}
