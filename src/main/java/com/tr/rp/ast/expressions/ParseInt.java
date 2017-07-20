package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Parse string as integer.
 */
public class ParseInt extends AbstractExpression {

	private final AbstractExpression e;
	
	public ParseInt(AbstractExpression e) {
		this.e = e;
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ParseInt((AbstractExpression)e.replaceVariable(a, b));
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		return e.needsRankExpressionTransformation();
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		return new ParseInt(e.doRankExpressionTransformation(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new ParseInt((AbstractExpression)e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		try {
			return Integer.parseInt(this.e.getValue(e, Type.STRING));
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
			return Integer.parseInt(this.e.getDefiniteValue(Type.STRING));
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

	@Override
	public int hashCode() {
		return e.hashCode();
	}

}
