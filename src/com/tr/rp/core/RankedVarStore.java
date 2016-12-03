package com.tr.rp.core;

public class RankedVarStore {

	public final VarStore v;
	public final int rank;
	
	public RankedVarStore(VarStore v, int rank) {
		this.v = v;
		this.rank = rank;
	}
	
	public String toString() {
		return "<" + rank + ": " + v + ">";
	}
}
