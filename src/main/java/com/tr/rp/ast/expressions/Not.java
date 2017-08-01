package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * The boolean NOT operator.
 */
public class Not extends AbstractExpression {

	private final AbstractExpression e;
	
	public Not(AbstractExpression e) {
		this.e = e;
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Not((AbstractExpression)e.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new Not(e.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new Not((AbstractExpression)e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return !this.e.getValue(e, Type.BOOL);
	}

	@Override
	public boolean hasDefiniteValue() {
		return e.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return !e.getDefiniteValue(Type.BOOL);
	}

	public String toString() {
		return "!" + e;
	}
	
	public boolean equals(Object o) {
		return (o instanceof Not) && ((Not)o).e.equals(e);
	}
	
	@Override
	public int hashCode() {
		return e.hashCode();
	}

}
