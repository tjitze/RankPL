package com.tr.rp.iterators.ranked;

import com.tr.rp.exceptions.RPLException;

public interface BranchingIteratorErrorHandler {

	public void handlExpError(RPLException exception) throws RPLException;

}
