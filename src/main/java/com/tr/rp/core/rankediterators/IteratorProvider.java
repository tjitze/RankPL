package com.tr.rp.core.rankediterators;

import com.tr.rp.exceptions.RPLException;

/**
 * An interface for objects that provide a ranked iterator.
 */
public interface IteratorProvider<T> {

	public RankedIterator<T> getIterator(RankedIterator<T> parent, ExecutionContext c) throws RPLException;
	
}
