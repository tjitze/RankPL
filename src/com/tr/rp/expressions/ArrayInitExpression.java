package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

/**
 * Array initialization expression. Constructs an array with given
 * dimensions. Allows an optional initialization expression, which
 * determines the value with which all elements are initialized. By
 * default, elements are initialized with 0.
 */

public class ArrayInitExpression extends Expression {

	private final Expression[] dimensions;
	private final Expression initExpr;
	
	public ArrayInitExpression(Expression initExpr, Expression ... dimensions) {
		this.dimensions = dimensions;
		this.initExpr = (initExpr == null)? new Literal<Integer>(0): initExpr;
	}
	
	@Override
	public boolean containsVariable(String var) {
		return Arrays.stream(dimensions).anyMatch(e -> e.containsVariable(var)) || initExpr.equals(var);
	}

	@Override
	public void getVariables(Set<String> list) {
		Arrays.stream(dimensions).forEach(e -> e.getVariables(list));
		initExpr.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		Expression[] newDimensions = new Expression[dimensions.length];
		for (int i = 0; i < dimensions.length; i++) {
			newDimensions[i] = (Expression)dimensions[i].replaceVariable(a, b);
		}
		return new ArrayInitExpression((Expression)initExpr.replaceVariable(a, b), newDimensions);
	}

	@Override
	public boolean hasRankExpression() {
		return Arrays.stream(dimensions).anyMatch(e -> e.hasRankExpression()) || initExpr.hasRankExpression();
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		Expression[] newDimensions = new Expression[dimensions.length];
		for (int i = 0; i < dimensions.length; i++) {
			newDimensions[i] = (Expression)dimensions[i].transformRankExpressions(v, rank);
		}
		return new ArrayInitExpression((Expression)initExpr.transformRankExpressions(v, rank), newDimensions);
	}

	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		for (int i = 0; i < dimensions.length; i++) {
			FunctionCall fc = dimensions[i].getEmbeddedFunctionCall();
			if (fc != null) {
				return fc;
			}
		}
		return initExpr.getEmbeddedFunctionCall();
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		Expression[] newDimensions = new Expression[dimensions.length];
		for (int i = 0; i < dimensions.length; i++) {
			newDimensions[i] = (Expression)dimensions[i].replaceEmbeddedFunctionCall(fc, var);
		}
		return new ArrayInitExpression(initExpr.replaceEmbeddedFunctionCall(fc, var), newDimensions);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		int[] dimensionValues = new int[dimensions.length];
		for (int i = 0; i < dimensions.length; i++) {
			dimensionValues[i] = dimensions[i].getIntValue(e);
		}
		return (PersistentList)content(0, dimensionValues, initExpr.getValue(e)).get();
	}
	
	private Supplier<Object> content(int i, int[] dimensionValues, Object initValue) {
		if (i >= dimensionValues.length) {
			return new Supplier<Object>() {
				@Override
				public Object get() {
					return initValue;
				}
			};
		} else {
			return new Supplier<Object>() {
				@Override
				public Object get() {
					return new PersistentList(dimensionValues[i], content(i + 1, dimensionValues, initValue));
				}
			};
		}
	}

	@Override
	public boolean hasDefiniteValue() {
		for (Expression e: dimensions) {
			if (!e.hasDefiniteValue()) {
				return false;
			}
		}
		return initExpr.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		int[] dimensionValues = new int[dimensions.length];
		for (int i = 0; i < dimensions.length; i++) {
			dimensionValues[i] = dimensions[i].getDefiniteIntValue();
		}
		return (PersistentList)content(0, dimensionValues, initExpr.getDefiniteValue()).get();
	}
	
	public String toString() {
		String indexString = "";
		if (dimensions.length > 0) {
			for (Expression e: dimensions) {
				indexString += "[" + e + "]";
			}
		}
		return "ARRAY" + indexString + "(" + initExpr + ")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof ArrayInitExpression) &&
				((ArrayInitExpression)o).initExpr.equals(initExpr) &&
				Arrays.deepEquals(((ArrayInitExpression)o).dimensions, dimensions);
	}

}
