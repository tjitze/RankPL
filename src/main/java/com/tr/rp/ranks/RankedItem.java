package com.tr.rp.ranks;

/**
 * A pair consisting of some object and a rank.
 */
public class RankedItem<T> {

	public final T item;
	public final int rank;
	
	public RankedItem(T v, int rank) {
		this.item = v;
		this.rank = rank;
	}
	
	public String toString() {
		return "<" + rank + ": " + item + ">";
	}
}
