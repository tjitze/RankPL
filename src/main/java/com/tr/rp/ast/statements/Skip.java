package com.tr.rp.ast.statements;

import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

public class Skip extends AbstractStatement {

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		return in;
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


}
