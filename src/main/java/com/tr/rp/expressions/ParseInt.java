package com.tr.rp.expressions;

import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;

/**
 * Parse string as integer.
 */
public class ParseInt extends Expression {

	private final Expression e;
	
	public ParseInt(Expression e) {
		this.e = e;
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ParseInt((Expression)e.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new ParseInt(e.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new ParseInt((Expression)e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		try {
			return Integer.parseInt(this.e.getStringValue(e));
		} catch (NumberFormatException nfe) {
			RPLMiscException ex = new RPLMiscException(nfe.getMessage());
			ex.setExpression(this);
			throw ex;
		}
	}

	@Override
	public boolean hasDefiniteValue() {
		return e.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		try {
			return Integer.parseInt(this.e.getDefiniteStringValue());
		} catch (NumberFormatException nfe) {
			RPLMiscException ex = new RPLMiscException(nfe.getMessage());
			ex.setExpression(this);
			throw ex;
		}
	}

	public String toString() {
		String es = e.toString();
		if (es.startsWith("(") && es.endsWith(")")) {
			es = es.substring(1, es.length()-1);
		}
		return "parseint(" + es + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof ParseInt) && ((ParseInt)o).e.equals(e);
	}

}
