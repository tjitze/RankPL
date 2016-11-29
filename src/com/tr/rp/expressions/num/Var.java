package com.tr.rp.expressions.num;

import com.tr.rp.core.VarStore;

public class Var extends NumExpression {

	public final String variable;
	
	public Var(String variable) {
		this.variable = variable;
	}

	@Override
	public int getVal(VarStore e) {
		return e.getValue(variable);
	}

	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		return this;
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}
	
}
