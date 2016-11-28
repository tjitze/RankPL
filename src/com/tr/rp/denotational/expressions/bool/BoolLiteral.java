package com.tr.rp.denotational.expressions.bool;

import com.tr.rp.denotational.core.DExpression;
import com.tr.rp.denotational.core.VarStore;

public class BoolLiteral extends BoolExp {

	public final boolean value;
	
	public BoolLiteral(boolean value) {
		this.value = value;
	}

	@Override
	public boolean isTrue(VarStore e) {
		return value;
	}
	
}
