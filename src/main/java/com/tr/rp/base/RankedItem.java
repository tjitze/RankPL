package com.tr.rp.base;

import java.util.Objects;

public final class RankedItem<T> {

	public final int rank;
	public final T item;
	
	public RankedItem(T item, int rank) {
		this.item = item;
		this.rank = rank;
	}
	
	public String toString() {
		return "<" + item + " (rank " + rank + ")>";
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
