package com.tr.rp.expressions;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;

/**
 * The plus (+) expression. Works on integers as usual, and on
 * strings as a concatenation operator. Also allows concatenation
 * of strings with other types.
 */
public class Plus extends Expression {

	private final Expression e1, e2;
	
	public Plus(Expression e1, Expression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	@Override
	public boolean containsVariable(String var) {
		return e1.containsVariable(var) || e2.containsVariable(var);
	}

	@Override
	public void getVariables(Set<String> list) {
		e1.getVariables(list);
		e2.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Plus((Expression)e1.replaceVariable(a, b), (Expression)e2.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return e1.hasRankExpression() || e2.hasRankExpression();
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new Plus((Expression)e1.transformRankExpressions(v, rank), (Expression)e2.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = e1.getEmbeddedFunctionCall();
		if (fc != null) return fc;
		return e2.getEmbeddedFunctionCall();
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new Plus((Expression)e1.replaceEmbeddedFunctionCall(fc, var), (Expression)e2.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore v) throws RPLException {
		return get(e1.getValue(v), e2.getValue(v));
	}
	
	@Override
	public boolean hasDefiniteValue() {
		return e1.hasDefiniteValue() && e2.hasDefiniteValue();
	}

	private Object get(Object o1, Object o2) throws RPLException {
		if (o1 == null) {
			throw new RPLUndefinedException(e1);
		}
		if (o2 == null) {
			throw new RPLUndefinedException(e2);
		}
		if (o1 instanceof Integer && o2 instanceof Integer) {
			return ((int)o1) + ((int)o2);
		}
		if (o1 instanceof String || o2 instanceof String) {
			return o1.toString() + o2.toString();
		}
		throw new RPLTypeError("int or string", o1);
	}
	
	@Override
	public Object getDefiniteValue() throws RPLException {
		return get(e1.getDefiniteValue(), e2.getDefiniteValue());
	}
	
	public Expression getE1() {
		return e1;
	}

	public Expression getE2() {
		return e2;
	}
	
	public String toString() {
		return "(" + e1 + " + " + e2 + ")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof Plus) &&
				((Plus)o).e1.equals(e1) &&
				((Plus)o).e2.equals(e2);
	}

}
