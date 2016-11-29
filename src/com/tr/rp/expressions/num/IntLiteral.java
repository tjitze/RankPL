package com.tr.rp.expressions.num;

import com.tr.rp.core.VarStore;

public class IntLiteral extends NumExpression {

	public final int value;
	
	public IntLiteral(int value) {
		this.value = value;
	}

	@Override
	public int getVal(VarStore e) {
		return value;
	}

	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		return this;
	}
	
}
