package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class CharToInt extends AbstractExpression {

	private final AbstractExpression charExp;
	
	public CharToInt(AbstractExpression charExp) {
		this.charExp = charExp;
	}

	@Override
	public void getVariables(Set<String> list) {
		charExp.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return charExp.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new CharToInt(charExp.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return charExp.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new CharToInt((AbstractExpression)charExp.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		String value = charExp.getValue(e, Type.STRING);
		if (value.length() != 1) {
			throw new RPLMiscException("CharToInt requires string of length one");
		}
		return (int)value.charAt(0);
	}

	@Override
	public boolean hasDefiniteValue() {
		return charExp.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		String value = charExp.getDefiniteValue(Type.STRING);
		if (value.length() != 1) {
			throw new RPLMiscException("CharToInt requires string of length one");
		}
		return (int)value.charAt(0);
	}

	public String toString() {
		return "charToInt(" + StringTools.stripPars(charExp.toString()) +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof CharToInt) && ((CharToInt)o).charExp.equals(charExp);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass(), charExp);
	}

}
