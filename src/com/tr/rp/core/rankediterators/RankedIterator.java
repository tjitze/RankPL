package com.tr.rp.core.rankediterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.bool.BoolExpression;

/**
 * Iterator that returns items associated with a rank,
 * in a low-to-high order. The first item must be ranked 0
 * or Integer.MAX_VALUE. This is an iterator that needs to
 * be initialized by calling next() first. The methods 
 * getItem() and getRank() are not required to return 
 * anything valid before the first call of the next() 
 * method.
 */
public interface RankedIterator {

	/**
	 * Get next item of iterator (accessible via getValue/getRank).
	 *
	 * @return False if there is no more item (in this case, 
	 * 		getValue()/getRank() may not return valid values).
	 */
	public boolean next();

	/**
	 * @return Current item
	 */
	public VarStore getItem();

	/**
	 * @return Rank of current item
	 */
	public int getRank();

	/**
	 * Optional method to return the rank of the lowest ranked
	 * item passed through by this iterator (used by observe/
	 * if-then-else).
	 */
	default public int getConditioningOffset() {
		return 0;
	}

}
