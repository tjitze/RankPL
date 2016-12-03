package com.tr.rp.expressions.bool;

import com.tr.rp.core.VarStore;

public class Not extends BoolExpression {

	public final BoolExpression e;
	
	public Not(BoolExpression e) {
		this.e = e;
	}

	@Override
	public boolean isTrue(VarStore env) {
		return !e.isTrue(env);
	}
	
	public BoolExpression negate() {
		return e;
	}

	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		BoolExpression te = e.transformRankExpressions(v, rank);
		if (e != te) {
			return new Not(te);
		} else {
			return this;
		}
	}
	
	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}
	
	public String toString() {
		return "Not(" + e + ")";
	}
}
