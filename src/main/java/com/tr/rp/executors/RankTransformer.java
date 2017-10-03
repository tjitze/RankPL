package com.tr.rp.executors;

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;

public final class RankTransformer<T extends AbstractExpression> implements Executor, Supplier<T> {

	private Executor out;
	private final T exp;
	private final LinkedList<State> queue = new LinkedList<State>();
	private T transformedExp = null;
	private AbstractStatement st;
	private boolean closed = false;
	
	public RankTransformer(T exp) {
		this.exp = exp;
		if (!exp.hasRankExpression()) {
			transformedExp = exp;
		}
	}
	
	public void setOutput(Executor out, AbstractStatement st) {
		Objects.nonNull(out);
		this.out = out;
		this.st = st;
	}
	
	public T get() {
		if (transformedExp == null) {
			throw new IllegalStateException("Expression not yet transformed");
		}
		return transformedExp;
	}
	
	@Override
	public void close() throws RPLException {
		closed = true;
		Objects.nonNull(out);
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
		if (closed) {
			throw new IllegalStateException();
		}
		Objects.nonNull(out);
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
				if (st != null) {
					e.setStatement(st);
				}
				throw e;
			}
		} else {
			out.push(s);
		}
	}

	public static <T extends AbstractExpression> RankTransformer<T> create(T e) {
		return new RankTransformer<T>(e);
	}
}
