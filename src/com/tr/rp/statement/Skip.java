package com.tr.rp.statement;

import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;

public class Skip implements DStatement {

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in) {
		return in;
	}

	public String toString() {
		return "skip";
	}
	
	public boolean equals(Object o) {
		return o instanceof Skip;
	}

	@Override
	public boolean containsVariable(String var) {
		return false;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public void getVariables(Set<String> list) { }
	
	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}

}
