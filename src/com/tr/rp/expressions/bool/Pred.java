package com.tr.rp.expressions.bool;

import java.util.function.Predicate;

import com.tr.rp.core.VarStore;

public class Pred extends BoolExpression {

	private final Predicate<VarStore> pred;
	
	public Pred(Predicate<VarStore> pred) {
		this.pred = pred;
	}
	
	@Override
	public boolean isTrue(VarStore e) {
		return pred.test(e);
	}

	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		return this;
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public boolean getDefiniteValue() {
		return false;
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}

}
