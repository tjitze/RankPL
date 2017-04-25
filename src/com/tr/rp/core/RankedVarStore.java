package com.tr.rp.core;

/**
 * A pair consisting of a variable store and a rank.
 */
public class RankedVarStore<T> {

	public final T varStore;
	public final int rank;
	
	public RankedVarStore(T v, int rank) {
		this.varStore = v;
		this.rank = rank;
	}
	
	public String toString() {
		return "<" + rank + ": " + varStore + ">";
	}
}
