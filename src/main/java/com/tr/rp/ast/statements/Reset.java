package com.tr.rp.ast.statements;

import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.InitialVarStoreIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

/**
 * The reset statement resets the complete program state. Used for testing.
 */
public class Reset extends AbstractStatement {
	
	public Reset() {
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		return new InitialVarStoreIterator();
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
