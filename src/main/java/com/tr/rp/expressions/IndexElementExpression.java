package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;

/**
 * Takes as input an expression that evaluates to an n-dimensional
 * array, and a sequence of n index expressions. Returns the element
 * of the array corresponding to the values of the index expressions.
 *  
 */
public class IndexElementExpression extends Expression {

	private final Expression exp;
	private final Expression[] indices;
	
	public IndexElementExpression(Expression exp, Expression ... indices) {
		this.exp = exp;
		this.indices = indices;
	}

	@Override
	public void getVariables(Set<String> list) {
		Arrays.stream(indices).forEach(e -> e.getVariables(list));
		exp.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		Expression[] newIndices = new Expression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (Expression)indices[i].replaceVariable(a, b);
		}
		Expression newExp = (Expression)exp.replaceVariable(a, b);
		return new IndexElementExpression(newExp, newIndices);
	}

	@Override
	public boolean hasRankExpression() {
		return exp.hasRankExpression() ||
				Arrays.stream(indices).anyMatch(e -> e.hasRankExpression());
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		Expression[] newIndices = new Expression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (Expression)indices[i].transformRankExpressions(v, rank);
		}
		Expression newExp = (Expression)exp.transformRankExpressions(v, rank);
		return new IndexElementExpression(newExp, newIndices);
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = exp.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		for (int i = 0; i < indices.length; i++) {
			fc = indices[i].getEmbeddedFunctionCall();
			if (fc != null) {
				return fc;
			}
		}
		return null;
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		Expression[] newIndices = new Expression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (Expression)indices[i].replaceEmbeddedFunctionCall(fc, var);
		}
		Expression newExp = (Expression)exp.replaceEmbeddedFunctionCall(fc, var);
		return new IndexElementExpression(newExp, newIndices);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		Object o = exp.getValue(e);
		if (o == null) {
			throw new RPLUndefinedException(this);
		}
		for (int i = 0; i < indices.length; i++) {
			if (o == null) {
				throw new RPLUndefinedException(this);
			} else if (o instanceof String) {
				// Allow strings to be referenced as 1D array
				String s = (String)o;
				int index = indices[i].getIntValue(e);
				if (index < 0 || index >= s.length()) {
					throw new RPLIndexOutOfBoundsException(index, s.length(), this);
				}
				o = s.substring(index, index + 1);
			} else if (o instanceof PersistentList) {
				PersistentList list = (PersistentList)o;
				int index = indices[i].getIntValue(e);
				if (index < 0 || index >= list.size()) {
					throw new RPLIndexOutOfBoundsException(index, list.size(), this);
				}
				o = list.get(index);
			} else {
				throw new RPLTypeError("list", o, this);
			}
		}
		return o;
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return null;
	}
	
	public String toString() {
		String indexString = "";
		if (indices.length > 0) {
			for (Expression e: indices) {
				indexString += "[" + e + "]";
			}
		}
		return exp + indexString;
	}
	
	public boolean equals(Object o) {
		return (o instanceof IndexElementExpression) &&
				((IndexElementExpression)o).exp.equals(exp) &&
				Arrays.deepEquals(((IndexElementExpression)o).indices, indices);
	}

	/**
	 * @return True iff value of this element is defined (not null)
	 */
	public boolean isDefined(VarStore e) throws RPLException {
		try {
			getValue(e);
		} catch (RPLUndefinedException ex) {
			return false;
		}
		return true;
	}

}
