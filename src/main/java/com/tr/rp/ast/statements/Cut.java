package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.varstore.types.Type;

/**
 * The Cut statement removes all alternatives with a rank equal to or higher
 * than the given rank. It can be used to speed up execution, or to implement
 * iterative deepening techniques.
 */
public class Cut extends AbstractStatement {
	
	private AbstractExpression rank;
	
	/**
	 * Construct Cut statement with given rank.
	 */
	public Cut(AbstractExpression rank) {
		this.rank = rank;
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		return new Executor() {

			private boolean closed = false;
			
			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				if (!closed && s.getRank() < rank.getValue(s.getVarStore(), Type.INT)) {
					out.push(s);
				} else {
					out.close();
					closed = true;
				}
			}
			
		};
	}

	public String toString() {
		return "cut " + rank;
	}
	
	public boolean equals(Object o) {
		return o instanceof Cut && ((Cut)o).rank == rank;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public void getVariables(Set<String> list) { }

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}
}
