package com.tr.rp.executors;

import java.util.HashSet;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

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
