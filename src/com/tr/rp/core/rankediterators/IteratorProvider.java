package com.tr.rp.core.rankediterators;

/**
 * An interface for objects that provide a ranked iterator.
 */
public interface IteratorProvider<T> {

	public RankedIterator<T> getIterator(RankedIterator<T> parent);
	
}
