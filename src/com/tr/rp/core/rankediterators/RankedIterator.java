package com.tr.rp.core.rankediterators;

import com.tr.rp.exceptions.RPLException;

/**
 * Iterator that returns items associated with ranks in a 
 * low-to-high order. This represents a ranking function 
 * over items that can be iterated through from most 
 * plausible (rank 0) to least plausible.
 * 
 * The following rules must be satisfied:
 * 1: When a ranked iterator is constructed, its state is
 *    "not initialized". The methods getItem() and getRank()
 *    will return an undefined value.
 * 2: If calling next() returns true then getItem() and
 *    getRank() return the next item and associated rank.
 *    If next() returns false, their value is undefined.
 * 3: The first call to next() (unless it returns false) will
 *    make getRank() return 0.
 * 4: Every subsequent call to next() (unless it returns false) will
 *    make getRank() return a rank greater or equal than the 
 *    previously returned rank.
 *    
 * Note that it is allowed (although generally not desired) to return
 * the same item more than once. To filter out duplicates, use the 
 * DuplicateRemovingIterator.
 */
public interface RankedIterator<T> {

	/**
	 * Get next item of iterator (accessible via getItem()/getRank()).
	 *
	 * @return False if there is no more item.
	 * @throws RPLException Run time RPL exception
	 */
	public boolean next() throws RPLException;

	/**
	 * @return Current item
	 * @throws RPLException Run time RPL exception
	 */
	public T getItem() throws RPLException;

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
