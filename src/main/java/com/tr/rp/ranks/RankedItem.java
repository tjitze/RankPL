package com.tr.rp.ranks;

import java.util.Objects;

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
	
	public boolean equals(Object o) {
		return o instanceof RankedItem && 
				((RankedItem<?>)o).item.equals(item) &&
				((RankedItem<?>)o).rank == rank; 

	}
	
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), item, rank);
	}
}
