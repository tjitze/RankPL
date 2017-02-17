package com.tr.rp.expressions.num;

import java.util.Arrays;
import java.util.Objects;

import com.tr.rp.core.VarStore;

public class IndexedVar extends NumExpression {

	public final String variable;
	public final NumExpression[] indexExpressions;
	
	public IndexedVar(String variable, NumExpression ... indexExpressions) {
		this.variable = variable;
		this.indexExpressions = indexExpressions;
	}

	public IndexedVar(String variable, String ... indexVariables) {
		this.variable = variable;
		this.indexExpressions = new NumExpression[indexVariables.length];
		for (int i = 0; i < indexVariables.length; i++) {
			indexExpressions[i] = new Var(indexVariables[i]);
		}
	}

	@Override
	public int getVal(VarStore vs) {
		Objects.requireNonNull(vs);
		int[] indexValues = new int[indexExpressions.length];
		for (int i = 0; i < indexExpressions.length; i++) {
			indexValues[i] = indexExpressions[i].getVal(vs);
		}
		return vs.getElementOfArray(variable, indexValues)
				.orElseThrow(()->new IndexOutOfBoundsException(
						"Index " + Arrays.toString(indexValues) + " for variable " + variable));
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
		return "_"+variable;
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public int getDefiniteValue() {
		return 0;
	}

	
}
