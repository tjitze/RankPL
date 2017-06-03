package com.tr.rp.statement;

import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;

public class Skip extends DStatement {

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
	public DStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}

}
