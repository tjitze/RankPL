package com.tr.rp.ast.statements;

import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.executors.Executor;

/**
 * The skip statement does nothing. Semantically it is equivalent 
 * to the identity function.
 */
public class Skip extends AbstractStatement {

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		return out;
	}	

	public String toString() {
		return "skip";
	}
	
	public boolean equals(Object o) {
		return o instanceof Skip;
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
