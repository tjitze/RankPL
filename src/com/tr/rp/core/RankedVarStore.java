package com.tr.rp.core;

/**
 * A pair consistong of a variable store and a rank.
 */
public class RankedVarStore {

	public final VarStore varStore;
	public final int rank;
	
	public RankedVarStore(VarStore v, int rank) {
		this.varStore = v;
		this.rank = rank;
	}
	
	public String toString() {
		return "<" + rank + ": " + varStore + ">";
	}
}
