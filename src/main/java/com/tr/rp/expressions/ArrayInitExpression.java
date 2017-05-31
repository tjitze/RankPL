package com.tr.rp.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;

/**
 * Array initialization expression. 
 * 
 * This expression evaluates to a one dimensional array whose size is the value of
 * the first argument.
 * 
 * If the second argument is present (optional) then the elements of the constructed 
 * array is initialized with the value of this argument. If the second argument is 
 * absent, then the elements of the array are not initialized.
 */
public class ArrayInitExpression extends Expression {

	private final Expression dimension;
	private final Expression initValue;
	
	public ArrayInitExpression(Expression dimension, Expression initValue) {
		this.dimension = dimension;
		this.initValue = initValue;
	}
		
	@Override
	public boolean containsVariable(String var) {
		return dimension.containsVariable(var) || (initValue != null && initValue.containsVariable(var));
	}

	@Override
	public void getVariables(Set<String> list) {
		dimension.getVariables(list);
		if (initValue != null) initValue.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ArrayInitExpression((Expression)dimension.replaceVariable(a, b), 
				(Expression)(initValue == null? null: initValue.replaceVariable(a, b)));
	}

	@Override
	public boolean hasRankExpression() {
		return dimension.hasRankExpression() || (initValue != null && initValue.hasRankExpression());
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		Expression newDimension = dimension.transformRankExpressions(v, rank);
		Expression newInitValue;
		if (initValue == null) {
			newInitValue = null;
		} else {
			newInitValue = initValue.transformRankExpressions(v, rank);
		}
		return new ArrayInitExpression(newDimension, newInitValue);
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = dimension.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		if (initValue != null) {
			return initValue.getEmbeddedFunctionCall();
		}
		return null;
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		Expression newDimension = dimension.replaceEmbeddedFunctionCall(fc, var);
		Expression newInitValue;
		if (initValue == null) {
			newInitValue = null;
		} else {
			newInitValue = initValue.replaceEmbeddedFunctionCall(fc, var);
		}
		return new ArrayInitExpression(newDimension, newInitValue);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		PersistentList list;
		Object dimensionValue = dimension.getValue(e);
		if (dimensionValue instanceof Integer) {
			if (initValue != null) {
				Object iv = initValue.getValue(e);
				list = new PersistentList((Integer)dimensionValue, () -> iv);
			} else {
				list = new PersistentList((Integer)dimensionValue);
			}
		} else {
			throw new RPLTypeError("Integer", dimensionValue);
		}
		return list;
	}

	@Override
	public boolean hasDefiniteValue() {
		return dimension.hasDefiniteValue() && (initValue == null || initValue.hasDefiniteValue());
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		PersistentList list;
		Object dimensionValue = dimension.getDefiniteValue();
		if (dimensionValue instanceof Integer) {
			if (initValue != null) {
				Object iv = initValue.getDefiniteValue();
				list = new PersistentList((Integer)dimensionValue, () -> iv);
			} else {
				list = new PersistentList((Integer)dimensionValue);
			}
		} else {
			throw new RPLTypeError("Integer", dimensionValue);
		}
		return list;
	}
	
	public String toString() {
		if (initValue != null) {
			return "array("+dimension+","+initValue+")";
		} else {
			return "array("+dimension+")";
		}
	}
	
	public boolean equals(Object o) {
		return (o instanceof ArrayInitExpression) &&
				((ArrayInitExpression)o).dimension.equals(dimension) &&
				Objects.equals(((ArrayInitExpression)o).initValue, initValue);
	}

}
