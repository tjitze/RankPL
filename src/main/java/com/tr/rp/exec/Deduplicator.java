package com.tr.rp.exec;

import java.util.HashSet;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

public class Deduplicator implements Executor {

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
