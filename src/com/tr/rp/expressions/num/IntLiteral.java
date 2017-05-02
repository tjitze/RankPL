package com.tr.rp.expressions.num;

import java.util.Set;

import com.tr.rp.core.LanguageElement;
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

	@Override
	public boolean containsVariable(String var) {
		return false;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		return null;
	}

	@Override
	public NumExpression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		return this;
	}

	@Override
	public void getVariables(Set<String> list) { }
	
}
