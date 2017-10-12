package com.tr.rp.executors;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

/**
 * Interface for objects that accept state push and close events. 
 * 
 * The following rules form the "contract" of this interface. They must be
 * followed when using objects that implement this interface, and they may
 * be assumed to hold when implementing the interface:
 * 
 * - States (which consist of a variable store and associated rank) must be 
 * pushed in a low-to-high rank order. I.e., is not allowed to push a state 
 * with rank N after having pushed a state with rank higher than N. 
 * 
 * - The first state that is pushed is ranked zero.
 * 
 * - After calling close(), no other calls to push(State s) or close() are
 * done.
 * 
 * When implementing this interface, it is not necessary to check correctness
 * (i.e., violations of the above rules) in an explicit way. A convenient way 
 * to add correctness checking (which can be enabled or disabled based on a 
 * static flag) is provided by the Guard executor. 
 */
public interface Executor {

	/**
	 * Close this executor. This signals that input is finished, and
	 * that no more states will be pushed.
	 * 
	 * @throws RPLException Execution exception.
	 */
	public void close() throws RPLException;
	
	/**
	 * Push state.
	 * 
	 * @param s State to push.
	 * @throws RPLException Execution exception.
	 */
	public void push(State s) throws RPLException;
	
	/**
	 * Push given variable store and rank.
	 * 
	 * @param vs Variable store to push,
	 * @param rank Rank of variable store.
	 * @throws RPLException Execution exception.
	 */
	public default void push(VarStore vs, int rank) throws RPLException {
		push(new State(vs, rank));
	}
}
