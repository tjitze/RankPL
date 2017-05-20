package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

/**
 * Array construction expression. Evaluates to an array with a given
 * set of values.
 */
public class ArrayConstructExpression extends Expression {

	private final Expression[] values;
	
	public ArrayConstructExpression(Expression ... values) {
		this.values = values;
	}
	
	public ArrayConstructExpression(int ... values) {
		this.values = new Expression[values.length];
		for (int i = 0; i < values.length; i++) {
			this.values[i] = new Literal<Integer>(values[i]);
		}
	}
	
	@Override
	public boolean containsVariable(String var) {
		return Arrays.stream(values).anyMatch(e -> e.containsVariable(var));
	}

	@Override
	public void getVariables(Set<String> list) {
		Arrays.stream(values).forEach(e -> e.getVariables(list));
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		Expression[] newValues = new Expression[values.length];
		for (int i = 0; i < values.length; i++) {
			newValues[i] = (Expression)values[i].replaceVariable(a, b);
		}
		return new ArrayConstructExpression(newValues);
	}

	@Override
	public boolean hasRankExpression() {
		return Arrays.stream(values).anyMatch(e -> e.hasRankExpression());
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		Expression[] newValues = new Expression[values.length];
		for (int i = 0; i < values.length; i++) {
			newValues[i] = (Expression)values[i].transformRankExpressions(v, rank);
		}
		return new ArrayConstructExpression(newValues);
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		for (int i = 0; i < values.length; i++) {
			AbstractFunctionCall fc = values[i].getEmbeddedFunctionCall();
			if (fc != null) {
				return fc;
			}
		}
		return null;
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		Expression[] newValues = new Expression[values.length];
		for (int i = 0; i < values.length; i++) {
			newValues[i] = (Expression)values[i].replaceEmbeddedFunctionCall(fc, var);
		}
		return new ArrayConstructExpression(newValues);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		Object[] evaluatedValues = new Object[values.length];
		for (int i = 0; i < values.length; i++) {
			evaluatedValues[i] = values[i].getValue(e);
		}
		return new PersistentList(evaluatedValues);
	}

	@Override
	public boolean hasDefiniteValue() {
		for (Expression e: values) {
			if (!e.hasDefiniteValue()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		Object[] dimensionValues = new Object[values.length];
		for (int i = 0; i < values.length; i++) {
			dimensionValues[i] = values[i].getDefiniteValue();
		}
		return new PersistentList(dimensionValues);
	}
	
	public String toString() {
		return "[" + Arrays.stream(values)
			.map(v -> v.toString())
			.collect(Collectors.joining(", ")) + "]";
	}
	
	public boolean equals(Object o) {
		return (o instanceof ArrayConstructExpression) &&
				Arrays.deepEquals(((ArrayConstructExpression)o).values, values);
	}

}
