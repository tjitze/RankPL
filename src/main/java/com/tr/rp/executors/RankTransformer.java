package com.tr.rp.executors;

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;

public class RankTransformer<T extends AbstractExpression> implements Executor, Supplier<T> {

	private Executor out;
	private final T exp;
	private final LinkedList<State> queue = new LinkedList<State>();
	private T transformedExp = null;
	private AbstractStatement st;
	
	public static <T extends AbstractExpression> RankTransformer<T> create(T e) {
		return new RankTransformer<T>(e);
	}

	public RankTransformer(T exp) {
		this.exp = exp;
		if (!exp.hasRankExpression()) {
			transformedExp = exp;
		}
	}
	
	private void setOutput(Executor out, AbstractStatement st) {
		Objects.nonNull(out);
		this.out = out;
		this.st = st;
	}
	
	public Executor getExecutor(Executor out, AbstractStatement st) {
		if (!exp.hasRankExpression()) {
			return out;
		} else {
			setOutput(out, st);
			return this;
		}
	}
	
	public Executor getExecutor(Executor out) {
		if (!exp.hasRankExpression()) {
			return out;
		} else {
			setOutput(out, null);
			return this;
		}
	}
	
	public T get() {
		if (transformedExp == null) {
			throw new IllegalStateException("Expression not yet transformed");
		}
		return transformedExp;
	}
	
	@Override
	public void close() throws RPLException {
		if (transformedExp == null) {
			try {
				@SuppressWarnings("unchecked")
				T exp2 = (T)exp.transformRankExpressions(Rank.MAX);
				transformedExp = exp2;
			} catch (RPLException e) {
				if (st != null) {
					e.setStatement(st);
				}
				throw e;
			}
			while (!queue.isEmpty()) {
				out.push(queue.removeFirst());
			}
		}
		out.close();
	}

	@Override
	public void push(State s) throws RPLException {
		if (transformedExp == null) {
			try {
				@SuppressWarnings("unchecked")
				T exp2 = (T)exp.transformRankExpressions(s.getVarStore(), s.getRank());
				if (!exp2.hasRankExpression()) {
					transformedExp = exp2;
					while (!queue.isEmpty()) {
						out.push(queue.removeFirst());
					}
					out.push(s);
				} else {
					queue.addLast(s);
				}
			} catch (RPLException e) {
				handleRankTransformException(e);
			}
		} else {
			out.push(s);
		}
	}

	protected void handleRankTransformException(RPLException e) throws RPLException {
		if (st != null) {
			e.setStatement(st);
		}
		throw e;
	}
	
}
