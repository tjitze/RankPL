package com.tr.rp.iterators.ranked;

import com.tr.rp.exceptions.RPLException;

public interface MergingIteratorErrorHandler {

	void handleRankExpressionError(RPLException e) throws RPLException;

	void illegalRank(int ri) throws RPLException;

}
