package com.tr.rp.expressions.num;

import java.util.Arrays;

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

	@Override
	public boolean hasRankExpression() {
		return false;
	}
	

	public String toString() {
		return "" + value;
	}

	@Override
	public boolean hasDefiniteValue() {
		return true;
	}

	@Override
	public int getDefiniteValue() {
		return value;
	}
	
	public boolean equals(Object o) {
		return o instanceof IntLiteral && ((IntLiteral)o).value == value;
	}
	
}
