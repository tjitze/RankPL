package com.tr.rp.core;

import com.tr.rp.core.rankediterators.IteratorProvider;
import com.tr.rp.statement.FunctionCallForm;

/**
 * Super class for RPL statements. This interface extends
 * the IteratorProvider interface: every statement must
 * provide an iterator that implements its semantics.
 */
public abstract class DStatement implements IteratorProvider<VarStore>, LanguageElement {
	
	private int lineNumber = -1;
	
	/**
	 * Rewrite embedded function calls to an executable form (see
	 * FunctionCallForm). Return the result.
	 * 
	 * @return Statement where embedded function calls are rewritten
	 */
	public abstract DStatement rewriteEmbeddedFunctionCalls();

	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
}
