package com.tr.rp.ast;

import java.util.Set;

import com.tr.rp.base.ExecutionContext;
import com.tr.rp.executors.Executor;

/**
 * Super class for RPL statements. This interface extends
 * the IteratorProvider interface: every statement must
 * provide an iterator that implements its semantics.
 */
public abstract class AbstractStatement implements LanguageElement {
	
	private int lineNumber = -1;
	
	/**
	 * Rewrite embedded function calls to an executable form (see
	 * FunctionCallForm). Return the result.
	 * 
	 * @return Statement where embedded function calls are rewritten
	 */
	public abstract AbstractStatement rewriteEmbeddedFunctionCalls();

	/**
	 * @return Line number in source file (-1 if not set).
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	
	/**
	 * Set the line number of this statement in the source file. Will be used
	 * for error reporting.
	 * 
	 * @param lineNumber Line number of this statement
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	/**
	 * Collect variables that are assigned by this statement
	 */
	public abstract void getAssignedVariables(Set<String> variables);

	/**
	 * Return executor for this statement
	 */
	public abstract Executor getExecutor(Executor out, ExecutionContext c);

	public abstract boolean equals(Object o);
	
	public abstract int hashCode();
}
