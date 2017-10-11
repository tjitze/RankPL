package com.tr.rp.ast.statements;

import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;

/**
 * The block statement blocks execution and yields an empty ranking.
 * This is equivalent to "observe false".
 */
public class Block extends AbstractStatement {

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		return new Executor() {
			private boolean closed = false;
			
			@Override
			public void close() throws RPLException {
				if (!closed) {
					closed = true;
					out.close();
				}
			}

			@Override
			public void push(State s) throws RPLException {
				close();
			}
		};
	}	

	public String toString() {
		return "block";
	}
	
	public boolean equals(Object o) {
		return o instanceof Block;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public void getVariables(Set<String> list) { 
		// nop
	}
	
	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}	

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}


}
