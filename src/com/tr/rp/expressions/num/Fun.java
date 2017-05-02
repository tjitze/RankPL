package com.tr.rp.expressions.num;

import java.util.Set;
import java.util.function.Function;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;

public class Fun extends NumExpression {

	private final Function<VarStore, Integer> fun;
	
	public Fun(Function<VarStore, Integer> fun) {
		this.fun = fun;
	}

	@Override
	public int getVal(VarStore e) {
		return fun.apply(e);
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public int getDefiniteValue() {
		return 0;
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}

	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		return this;
	}

	public boolean equals(Object o) {
		return o instanceof Fun && ((Fun)o).fun == fun;
	}

	@Override
	public boolean containsVariable(String var) {
		throw new UnsupportedOperationException("Cannot check for variable occurrence in Fun objects");
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		throw new UnsupportedOperationException("Cannot replace variables in Fun objects");
	}
	
	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		return null;
	}

	@Override
	public NumExpression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		return this;
	}

	@Override
	public void getVariables(Set<String> list) { }
}
