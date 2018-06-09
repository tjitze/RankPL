package com.tr.rp.executors;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * The Filter executor implements the regular conditioning operator. It takes as 
 * input another executor, to which it passes on only states satisfying a given 
 * condition. Ranks of states that are passed on are shifted down uniformly so 
 * that the lowest ranked state that is passed on is ranked zero.
 */
public class Filter implements Executor {

	private final Executor out;
	private final Predicate predicate;
	private int offset = -1;
	private Consumer<Integer> offsetListener;
	
	public Filter(Executor out, Predicate predicate) {
		this.out = out;
		this.predicate = predicate;
	}
	
	public Filter(Executor out, Supplier<AbstractExpression> expSupplier) {
		this.out = out;
		this.predicate = new Predicate() {
			@Override
			public boolean test(State s) throws RPLException {
				return expSupplier.get().getValue(s.getVarStore(), Type.BOOL);
			}
		};
	}
	
	public Filter(Executor out, AbstractExpression exp) {
		this.out = out;
		this.predicate = new Predicate() {
			@Override
			public boolean test(State s) throws RPLException {
				return exp.getValue(s.getVarStore(), Type.BOOL);
			}
		};
	}
	
	@Override
	public void close() throws RPLException {
		out.close();
	}
	
	@Override
	public void push(State s) throws RPLException {
		if (getCheckedValue(s)) {
			if (offset == -1) {
				offset = s.getRank();
				if (offsetListener != null) {
					offsetListener.accept(offset);
				}
			}
			out.push(s.shiftDown(offset));
		}
 	}

	private boolean getCheckedValue(State s) throws RPLException {
		try {
			return predicate.test(s);
		} catch (RPLException e) {
			handleConditionException(e);
			return false;
		}
	}

	/**
	 * Override to handle exceptions resulting from evaluation of condition
	 */
	public void handleConditionException(RPLException e) throws RPLException {
		throw e;
	}

	public void setOffsetListener(Consumer<Integer> offsetListener) {
		this.offsetListener = offsetListener;
	}
	
	public static abstract class Predicate {
		public abstract boolean test(State s) throws RPLException;
	}

}
