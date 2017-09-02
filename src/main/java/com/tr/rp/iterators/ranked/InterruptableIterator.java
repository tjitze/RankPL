package com.tr.rp.iterators.ranked;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLInterruptedException;

/**
 * A ranked iterator that reproduces its input but throws an
 * RPLInterruptedException once a given predicate evaluates to true.
 */
public class InterruptableIterator<T> implements RankedIterator<T> {
	
	private final RankedIterator<T> in;
	private final BooleanSupplier interruptCondition;
	
	public InterruptableIterator(RankedIterator<T> in, BooleanSupplier interruptCondition) {
		this.in = in;
		this.interruptCondition = interruptCondition;
	}

	@Override
	public boolean next() throws RPLException {
		if (interruptCondition.getAsBoolean()) {
			throw new RPLInterruptedException();
		}
		return in.next();
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
