package com.tr.rp.expressions.num;

import java.util.function.Function;

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

}
