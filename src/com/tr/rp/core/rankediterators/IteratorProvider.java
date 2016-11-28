package com.tr.rp.core.rankediterators;

public interface IteratorProvider<V> {

	public RankedIterator<V> getIterator(RankedIterator<V> parent);
	
}
