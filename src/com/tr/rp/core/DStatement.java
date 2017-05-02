package com.tr.rp.core;

import com.tr.rp.core.rankediterators.IteratorProvider;
import com.tr.rp.expressions.num.FunctionCall;
import com.tr.rp.statement.FunctionCallForm;

/**
 * Interface for RPL statements. This interface extends
 * the IteratorProvider interface: every statement must
 * provide an iterator that implements its semantics.
 */
public interface DStatement extends IteratorProvider<VarStore>, LanguageElement {
	
	/**
	 * Rewrite embedded function calls to an executable form (see
	 * FunctionCallForm). Return the result.
	 * 
	 * @return Statement where embedded function calls are rewritten
	 */
	public DStatement rewriteEmbeddedFunctionCalls();

}
