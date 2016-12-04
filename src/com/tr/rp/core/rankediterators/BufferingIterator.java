package com.tr.rp.core.rankediterators;

import java.util.LinkedList;

import com.tr.rp.core.RankedVarStore;
import com.tr.rp.core.VarStore;

public class BufferingIterator implements RankedIterator {
	
	private LinkedList<RankedVarStore> queue = new LinkedList<RankedVarStore>();
	private int index = -1;
	private RankedIterator in;
	private boolean stopped = false;
	
	public BufferingIterator(RankedIterator in) {
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
	 * 0 will be returned after calling next().
	 * @param newIndex
	 */
	public void reset(int newIndex) {
		if (stopped) throw new IllegalStateException();
		if (newIndex < -1 || newIndex > index) {
			throw new IndexOutOfBoundsException("Illegal index: " + newIndex + " (valid range is -0.." + index + ")");
		}
		this.index = newIndex;
	}

	public int getIndex() {
		return index;
	}
	
	/**
	 * Stop buffering elements. This will clear the history of
	 * buffered items and prevent future calls to next() to 
	 * be buffered. After calling this method, the reset() 
	 * method will throw an illegal state exception.
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
	public boolean next() {
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
			if (v) queue.addLast(new RankedVarStore(in.getItem(), in.getRank()));
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
	public VarStore getItem() {
		return index >= 0 && index < queue.size()? queue.get(index).v: null;
	}

	@Override
	public int getRank() {
		return index >= 0 && index < queue.size()? queue.get(index).rank: Integer.MAX_VALUE;
	}

	public String toString() {
		return "BufferedIterator(in="+in+", index=" + index + ", queue=" + queue +")";
	}
}
