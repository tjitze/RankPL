package com.tr.rp.core.rankediterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.bool.BoolExpression;

/**
 * Iterator that returns variable stores associated with 
 * a rank, in a low-to-high order. This represents a ranking
 * function over variable stores that can be iterated through
 * from most plausible (rank 0) to least plausible.
 * 
 * The following rules must be satisfied:
 * 1: When a ranked iterator is constructed, its state is
 *    "not initialized". The methods getVarStore() and getRank()
 *    will return an undefined value.
 * 2: If calling next() returns true then getVarStore() and
 *    getRank() return the next variable store and associated rank.
 *    If next() returns false, their value is undefined.
 * 3: The first call to next() (unless it returns false) will
 *    make getRank() return 0.
 * 4: Every subsequent call to next() (unless it returns false) will
 *    make getRank() return a rank greater or equal than the 
 *    previously returned rank.
 *    
 * Note that it is allowed (although generally not desired) to return
 * the same variable store more than once. To filter out duplicates,
 * use the DuplicateRemovingIterator.
 */
public interface RankedIterator {

	/**
	 * Get next item of iterator (accessible via getVarStore()/getRank()).
	 *
	 * @return False if there is no more item.
	 */
	public boolean next();

	/**
	 * @return Current item
	 */
	public VarStore getVarStore();

	/**
	 * @return Rank of current item
	 */
	public int getRank();

	/**
	 * Optional method to return the prior rank of the lowest ranked
	 * item passed through by this iterator (used by observe and
	 * if-then-else).
	 */
	default public int getConditioningOffset() {
		return 0;
	}

}
