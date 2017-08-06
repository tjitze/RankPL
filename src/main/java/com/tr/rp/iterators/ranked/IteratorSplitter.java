package com.tr.rp.iterators.ranked;

import java.util.LinkedList;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

/**
 * An IteratorSplitter takes a ranked iterator as input and produces two copies (getA(), getB()). 
 */
public class IteratorSplitter<T> {

	private final RankedIterator<T> in;
	private final LinkedList<T> items = new LinkedList<T>();
	private final LinkedList<Integer> ranks = new LinkedList<Integer>();

	private int indexA = -1;
	private int indexB = -1;
	
	public IteratorSplitter(RankedIterator<T> in) {
		this.in = in;
	}
	
	/**
	 * @return The first copy of the iterator provided at construction.
	 */
	public RankedIterator<T> getA() {
		return new RankedIterator<T>() {

			@Override
			public boolean next() throws RPLException {
				indexA++;
				if (indexA == items.size()) {
					if (in.next()) {
						T item = in.getItem();
						int rank = in.getRank();
						items.addLast(item);
						ranks.addLast(rank);
					}
				}
				int firstIndexToKeep = Math.min(indexA, indexB);
				for (int i = 0; i < firstIndexToKeep; i++) {
					items.removeFirst();
					ranks.removeFirst();
					indexA--;
					indexB--;
				}
				return indexA < items.size();
			}

			@Override
			public T getItem() throws RPLException {
				return indexA >= 0 && indexA < items.size()? items.get(indexA): null;
			}

			@Override
			public int getRank() {
				return indexA >= 0 && indexA < ranks.size()? ranks.get(indexA): -1;
			}
			
		};
	}

	/**
	 * @return The second copy of the iterator provided at construction.
	 */
	public RankedIterator<T> getB() {
		return new RankedIterator<T>() {

			@Override
			public boolean next() throws RPLException {
				indexB++;
				if (indexB == items.size()) {
					if (in.next()) {
						T item = in.getItem();
						int rank = in.getRank();
						items.addLast(item);
						ranks.addLast(rank);
					}
				}
				int firstIndexToKeep = Math.min(indexA, indexB);
				for (int i = 0; i < firstIndexToKeep; i++) {
					items.removeFirst();
					ranks.removeFirst();
					indexA--;
					indexB--;
				}
				return indexB < items.size();
			}

			@Override
			public T getItem() throws RPLException {
				return indexB >= 0 && indexB < items.size()? items.get(indexB): null;
			}

			@Override
			public int getRank() {
				return indexB >= 0 && indexB < ranks.size()? ranks.get(indexB): -1;
			}
			
		};	
	}
	
	public String toString() {
		return "IteratorSplitter(in="+in+")";
	}

}
