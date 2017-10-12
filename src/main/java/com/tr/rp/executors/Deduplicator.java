package com.tr.rp.executors;

import java.util.HashSet;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

/**
 * The deduplicator executor passes through its input to a given output 
 * executor unchanged, except that variable stores that have been seen before
 * are skipped.
 * 
 * Because lower-ranked variable stores are pushed before higher ranked ones,
 * this means that the lowest-ranked variable store is passed through, while
 * higher ranked occurrences of the same variable store are skipped. This is 
 * semantically valid.
 * 
 * Using the deduplicator increases performance since it avoids redundant
 * computation, at the price of increased memory use, as variable stores that
 * have been seen have to be stored.
 */
public final class Deduplicator implements Executor {

	private final Executor out;
	private final HashSet<VarStore> seen = new HashSet<VarStore>();
	
	public Deduplicator(Executor out) {
		this.out = out;
	}
	
	@Override
	public void close() throws RPLException {
		seen.clear();
		out.close();
	}

	@Override
	public void push(State s) throws RPLException {
		if (!seen.contains(s.getVarStore())) {
			out.push(s);
			seen.add(s.getVarStore());
		}
	}

}
