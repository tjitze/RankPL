package com.tr.rp.denotational.core.rankediterators;

import com.tr.rp.denotational.core.VarStore;

public class InitialVarStoreIterator implements RankedIterator<VarStore> {
	
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
