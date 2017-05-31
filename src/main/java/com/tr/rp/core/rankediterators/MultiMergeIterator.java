package com.tr.rp.core.rankediterators;

import java.util.ArrayList;
import java.util.List;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;

public abstract class MultiMergeIterator<T> implements RankedIterator<T> {

	private List<RankedIterator<T>> iq = new ArrayList<RankedIterator<T>>();
	private List<Integer> rq = new ArrayList<Integer>();
	
	private T currentItem;
	private int currentRank;
	
	// Should provide current item unless inputFinished = true
	private RankedIterator<T> input;
	private boolean inputFinished = false;
	
	public MultiMergeIterator(RankedIterator<T> input) throws RPLException {
		this.input = input;
		init();
	}
	
	private void init() throws RPLException {
		if (!input.next()) {
			inputFinished = true;
		}
	}
	
	@Override
	public boolean next() throws RPLException {
		boolean hasNext = fetchNext();
		while (!hasNext && !rq.isEmpty()) {
			currentRank++;
			hasNext = fetchNext();
		}
		return hasNext;
	}

	@Override
	public T getItem() throws RPLException {
		return currentItem;
	}

	@Override
	public int getRank() {
		return currentRank;
	}

	/**
	 * Fetch next item of given rank. Returns false if there is no such item.
	 * @throws RPLException 
	 */
	private boolean fetchNext() throws RPLException {
		for (int i = 0; i < iq.size(); i++) {
			if (rq.get(i) + iq.get(i).getRank() <= currentRank) {
				currentItem = iq.get(i).getItem();
				if (rq.get(i) + iq.get(i).getRank() < currentRank) {
					throw new RPLMiscException("Internal error (unexpected rank ordering)");
				}
				if (!iq.get(i).next()) {
					iq.remove(i);
					rq.remove(i);
				}
				return true;
			}
		}
		// fetch next iterator if this might provide result
		while (!inputFinished && (input.getRank() <= currentRank || iq.isEmpty())) {
			T v = input.getItem();
			int prior = input.getRank();
			if (!input.next()) { // update inputFinished flag
				inputFinished = true;
			}
			RankedIterator<T> it = transform(v);
			if (it.next()) {
				currentItem = it.getItem();
				if (it.getRank() + prior < currentRank) {
					throw new RPLMiscException("Internal error (unexpected rank ordering)");
				}
				if (it.next()) { // only remember this iterator if it has a next item
					iq.add(it);
					rq.add(prior);
				}
				return prior <= currentRank;
			}
		}
		return false;
	}

	public abstract RankedIterator<T> transform(T v) throws RPLException;
	
}
