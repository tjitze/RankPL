package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class IntToChar extends AbstractExpression {

	private final AbstractExpression intExp;
	
	public IntToChar(AbstractExpression charExp) {
		this.intExp = charExp;
	}

	@Override
	public void getVariables(Set<String> list) {
		intExp.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return intExp.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new IntToChar(intExp.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return intExp.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new IntToChar((AbstractExpression)intExp.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		int value = intExp.getValue(e, Type.INT);
		return "" + ((char)value);
	}

	@Override
	public boolean hasDefiniteValue() {
		return intExp.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		int value = intExp.getDefiniteValue(Type.INT);
		return "" + ((char)value);
	}

	public String toString() {
		return "intToChar(" + intExp +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof IntToChar) && ((IntToChar)o).intExp.equals(intExp);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass(), intExp);
	}

}
