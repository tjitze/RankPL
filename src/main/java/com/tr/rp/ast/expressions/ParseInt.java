package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
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
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new ParseInt(e.transformRankExpressions(v, rank));
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
		return "parseint(" + StringTools.stripPars(e.toString()) + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof ParseInt) && ((ParseInt)o).e.equals(e);
	}

	@Override
	public int hashCode() {
		return e.hashCode();
	}

}
