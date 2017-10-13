package com.tr.rp.ast.statements;

import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.varstore.VarStoreFactory;

/**
 * The reset statement resets the complete program state. Used for testing.
 */
public class Reset extends AbstractStatement {
	
	public Reset() {
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		return new Executor() {
			private boolean done = false;
			@Override
			public void close() throws RPLException {
				if (!done) {
					done = true;
					out.push(new State(VarStoreFactory.getInitialvarStore(), 0));
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
		return "reset";
	}
	
	public boolean equals(Object o) {
		return o instanceof Reset;
	}

	@Override
	public int hashCode() {
		return 1;
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
