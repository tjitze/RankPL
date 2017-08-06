package com.tr.rp.iterators.ranked;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.RankedItem;

/**
 * A buffering iterator with a one-element buffer. Implements
 * a moveBack function that allows one to step back *one* element
 * but not more.
 */
public class SingleBufferingIterator<T> implements RankedIterator<T> {
	
	private RankedItem<T> previousItem;
	private RankedIterator<T> in;
	private boolean movedBack = false;
	private boolean done = false;
	private boolean initialized = false;
	
	public SingleBufferingIterator(RankedIterator<T> in) {
		this.in = in;
	}
	
	/**
	 * Move back one item
	 */
	public void moveBack() {
		if (movedBack) {
			throw new IllegalStateException("Can only move back one step");
		}
		if (previousItem == null) {
			throw new IllegalStateException("Can only move back after next() called");
		}
		movedBack = true;
	}
	
	public boolean canMoveBack() {
		return !movedBack && initialized;
	}
	
	@Override
	public boolean next() throws RPLException {
		if (movedBack) {
			movedBack = false;
			return !done;
		} else {
			if (!done && initialized) {
				previousItem = new RankedItem<T>(in.getItem(), in.getRank());
			} else {
				initialized = true;
			}
			done = !in.next();
			return !done;
		}
	}

	@Override
	public T getItem() throws RPLException {
		return movedBack? previousItem.item: in.getItem();
	}

	@Override
	public int getRank() {
		return movedBack? previousItem.rank: in.getRank();
	}

}
