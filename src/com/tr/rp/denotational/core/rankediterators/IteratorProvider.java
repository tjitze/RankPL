package com.tr.rp.denotational.core.rankediterators;

public interface IteratorProvider<V> {

	public RankedIterator<V> getIterator(RankedIterator<V> parent);
	
}
