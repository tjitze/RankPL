package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class StartsWith extends AbstractExpression {

	private final AbstractExpression str;
	private final AbstractExpression prefix;

	public StartsWith(AbstractExpression strExp, AbstractExpression prefixExp) {
		this.str = strExp;
		this.prefix = prefixExp;
	}

	@Override
	public void getVariables(Set<String> list) {
		str.getVariables(list);
		prefix.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return str.hasRankExpression() || prefix.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression e = new StartsWith((AbstractExpression) str.transformRankExpressions(v, rank),
				(AbstractExpression) prefix.transformRankExpressions(v, rank));
		e.setLineNumber(getLineNumber());
		return e;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = str.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		return prefix.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AbstractExpression e = new StartsWith((AbstractExpression) str.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression) prefix.replaceEmbeddedFunctionCall(fc, var));
		e.setLineNumber(getLineNumber());
		return e;
	}

	@Override
	public Object getValue(VarStore vs) throws RPLException {
		String s = str.getValue(vs, Type.STRING);
		String p = prefix.getValue(vs, Type.STRING);
		return s.startsWith(p);
	}

	@Override
	public boolean hasDefiniteValue() {
		return str.hasDefiniteValue() && prefix.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		String s = str.getDefiniteValue(Type.STRING);
		String p = prefix.getDefiniteValue(Type.STRING);
		return s.startsWith(p);
	}

	public String toString() {
		return "StartsWith(" + StringTools.stripPars(str.toString()) + "," + StringTools.stripPars(prefix.toString()) + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof StartsWith) 
				&& ((StartsWith) o).str.equals(str) 
				&& ((StartsWith) o).prefix.equals(prefix);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), str, prefix);
	}

}
