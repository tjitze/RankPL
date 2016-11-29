package com.tr.rp.expressions.bool;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.VarStore;

public class BoolLiteral extends BoolExp {

	public final boolean value;
	
	public BoolLiteral(boolean value) {
		this.value = value;
	}

	@Override
	public boolean isTrue(VarStore e) {
		return value;
	}

	@Override
	public BoolExp transformRankExpressions(VarStore v, int rank) {
		return this;
	}
	
}
