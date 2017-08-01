package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentArray;

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
public class ArrayInitExpression extends AbstractExpression {

	private final AbstractExpression dimension;
	private final AbstractExpression initValue;
	
	public ArrayInitExpression(AbstractExpression dimension, AbstractExpression initValue) {
		this.dimension = dimension;
		this.initValue = initValue;
	}

	@Override
	public void getVariables(Set<String> list) {
		dimension.getVariables(list);
		if (initValue != null) initValue.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ArrayInitExpression((AbstractExpression)dimension.replaceVariable(a, b), 
				(AbstractExpression)(initValue == null? null: initValue.replaceVariable(a, b)));
	}

	@Override
	public boolean hasRankExpression() {
		return dimension.hasRankExpression() || (initValue != null && initValue.hasRankExpression());
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression newDimension = dimension.transformRankExpressions(v, rank);
		AbstractExpression newInitValue;
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
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AbstractExpression newDimension = dimension.replaceEmbeddedFunctionCall(fc, var);
		AbstractExpression newInitValue;
		if (initValue == null) {
			newInitValue = null;
		} else {
			newInitValue = initValue.replaceEmbeddedFunctionCall(fc, var);
		}
		return new ArrayInitExpression(newDimension, newInitValue);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		PersistentArray list;
		Object dimensionValue = dimension.getValue(e);
		if (dimensionValue instanceof Integer) {
			if (initValue != null) {
				Object iv = initValue.getValue(e);
				list = new PersistentArray((Integer)dimensionValue, () -> iv);
			} else {
				list = new PersistentArray((Integer)dimensionValue);
			}
		} else {
			throw new RPLTypeError("Integer", dimensionValue, dimension);
		}
		return list;
	}

	@Override
	public boolean hasDefiniteValue() {
		return dimension.hasDefiniteValue() && (initValue == null || initValue.hasDefiniteValue());
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		PersistentArray list;
		Object dimensionValue = dimension.getDefiniteValue();
		if (dimensionValue instanceof Integer) {
			if (initValue != null) {
				Object iv = initValue.getDefiniteValue();
				list = new PersistentArray((Integer)dimensionValue, () -> iv);
			} else {
				list = new PersistentArray((Integer)dimensionValue);
			}
		} else {
			throw new RPLTypeError("Integer", dimensionValue, dimension);
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

	@Override
	public int hashCode() {
		return Objects.hash(dimension, initValue);
	}

}
