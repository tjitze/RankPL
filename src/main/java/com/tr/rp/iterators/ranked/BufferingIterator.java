package com.tr.rp.iterators.ranked;

import java.util.LinkedList;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.Rank;
import com.tr.rp.ranks.RankedItem;
import com.tr.rp.varstore.VarStore;

/**
 * A buffering iterator. Yields the same items and ranks as the
 * ranked iterator provided at construction, except that it can
 * be reset to iterate starting from an earlier item.
 */
public class BufferingIterator<T> implements RankedIterator<T> {
	
	private LinkedList<RankedItem<T>> queue = new LinkedList<RankedItem<T>>();
	private int index = -1;
	private RankedIterator<T> in;
	private boolean stopped = false;
	
	/**
	 * Construct buffering iterator.
	 * 
	 * @param in Iterator to use as input.
	 */
	public BufferingIterator(RankedIterator<T> in) {
		this.in = in;
	}
	
	/**
	 * Resets the buffer so that the iterator starts again
	 * from the beginning. The iterator will be in an initial
	 * state, meaning that it is required to call next() to
	 * get the first element.
	 */
	public void reset() {
		if (stopped) throw new IllegalStateException();
		index = -1;
	}
	
	/**
	 * Reset buffer. Doing a reset with -1 will bring
	 * the iterator in uninitialized state, so that item
	 * 0 will be returned after calling next(). Doing a
	 * reset with 0 will make getVarStore()/getRank() 
	 * return the first item, etc. 
	 * 
	 * Throws IndexOutOfBoundsException if index is illegal.
	 * 
	 * Throws IllegalStateException if stopBuffering() was called.
	 * 
	 * @param newIndex The reset index
	 */
	public void reset(int newIndex) {
		if (stopped) throw new IllegalStateException();
		if (newIndex < -1 || newIndex > index) {
			throw new IndexOutOfBoundsException("Illegal index: " + newIndex + " (valid range is 0.." + index + ")");
		}
		this.index = newIndex;
	}

	/**
	 * Move back one item
	 */
	public void moveBack() {
		reset(getIndex() - 1);
	}
	
	/**
	 * The current buffer index. This is -1 if not initialized,
	 * 0 for the first element, etc.
     *
	 * @return Current buffer index.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Stop buffering elements. This will clear the history of
	 * buffered items and prevent future calls to next() to 
	 * be buffered. After calling this method, the reset() 
	 * method will throw an illegal state exception. This method
	 * does not change the value returned by getVarStore() and
	 * getRank().
	 */
	public void stopBuffering() {
		stopped = true;
		clearHistory();
	}
	
	private void clearHistory() {
		while (index > 0) {
			queue.removeFirst();
			index--;
		}
	}
	
	@Override
	public boolean next() throws RPLException {
		if (index < queue.size() - 1) {
			index++;
			if (stopped) {
				clearHistory();
			}
			return true;
		}
		else if (index == queue.size() - 1) {
			index++;
			boolean v = in.next();
			if (v) queue.addLast(new RankedItem<T>(in.getItem(), in.getRank()));
			if (stopped) {
				clearHistory();
			}
			return index < queue.size();
		}
		else {
			return false;
		}
		// Post condition:
		// Index points to active queue element (or is -1 if not initialized)
		// If index points beyond active queue element then end.
	}

	@Override
	public T getItem() throws RPLException {
		return index >= 0 && index < queue.size()? queue.get(index).item: null;
	}

	@Override
	public int getRank() {
		return index >= 0 && index < queue.size()? queue.get(index).rank: Rank.MAX;
	}

	public String toString() {
		return "BufferedIterator(in="+in+", index=" + index + ", queue=" + queue +")";
	}
}
