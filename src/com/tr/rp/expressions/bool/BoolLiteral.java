package com.tr.rp.expressions.bool;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.bool.BoolExpression.Result;
import com.tr.rp.expressions.num.NumExpression;

public class BoolLiteral extends BoolExpression {

	public final boolean value;
	
	public BoolLiteral(boolean value) {
		this.value = value;
	}

	@Override
	public boolean isTrue(VarStore e) {
		return value;
	}

	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		return this;
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}
	
	public String toString() {
		return ""+value;
	}
	
	@Override
	public boolean hasDefiniteValue() {
		return true;
	}

	@Override
	public boolean getDefiniteValue() {
		return value;
	}
}
