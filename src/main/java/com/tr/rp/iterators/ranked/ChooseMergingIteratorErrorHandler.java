package com.tr.rp.iterators.ranked;

import com.tr.rp.exceptions.RPLException;

public interface ChooseMergingIteratorErrorHandler {

	void handleRankExpressionError(RPLException e) throws RPLException;

}
