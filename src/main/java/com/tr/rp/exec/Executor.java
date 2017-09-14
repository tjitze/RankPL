package com.tr.rp.exec;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

public interface Executor {

	public void close() throws RPLException;
	
	public void push(State s) throws RPLException;
	
	public default void push(VarStore vs, int rank) throws RPLException {
		push(new State(vs, rank));
	}
}
