package com.tr.rp.core.rankediterators;

import java.util.function.Consumer;
import java.util.function.Function;

import com.tr.rp.exceptions.RPLException;

/**
 * A ranked iterator that reproduces its input but ends once
 * a given maximum rank has been reached.
 */
public class RestrictIterator<T> implements RankedIterator<T> {

	public final int maxRank;
	private final RankedIterator<T> in;
	private Consumer<Integer> cutOffListener;
	
	/**
	 * Construct iterator that reproduces in, but stops when a the given rank
	 * maxRank has been reached.
	 * 
	 * @param in Iterator to reproduce
	 * @param maxRank Cut-off rank
	 */
	public RestrictIterator(RankedIterator<T> in, int maxRank) {
		this.in = in;
		this.maxRank = maxRank;
	}
	
	/**
	 * Construct restrict iterator with cut-off listener, which is called when the
	 * the input is cut off, but never called when maxRank is not reached.
	 * 
	 * @param in Iterator to reproduce
	 * @param maxRank Cut-off rank
	 * @param cutOffListener The cut-off listener
	 */
	public RestrictIterator(RankedIterator<T> in, int maxRank, Consumer<Integer> cutOffListener) {
		this.in = in;
		this.maxRank = maxRank;
		this.cutOffListener = cutOffListener;
	}
	
	@Override
	public boolean next() throws RPLException {
		boolean next = in.next();
		if (next) {
			if (in.getRank() < maxRank) {
				return true;
			} else {
				if (cutOffListener != null) {
					cutOffListener.accept(in.getRank());
				}
			}
		}
		return false;
	}

	@Override
	public T getItem() throws RPLException {
		return in.getItem();
	}

	@Override
	public int getRank() {
		return in.getRank();
	}

}
