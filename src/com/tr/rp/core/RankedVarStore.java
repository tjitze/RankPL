package com.tr.rp.core;

public class RankedVarStore {

	public final int rank;
	public final VarStore varStore;
	
	public RankedVarStore(int rank, VarStore varStore) {
		this.rank = rank;
		this.varStore = varStore;
	}
	
	public RankedVarStore create(int r) {
		return new RankedVarStore(r, varStore);
	}
}
