package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.arrays.PersistentArray;
import com.tr.rp.varstore.arrays.PersistentObjectArray;
import com.tr.rp.varstore.datastructures.PersistentList;
import com.tr.rp.varstore.datastructures.PersistentMap;
import com.tr.rp.varstore.datastructures.PersistentSet;
import com.tr.rp.varstore.datastructures.PersistentStack;
import com.tr.rp.varstore.types.Type;

/**
 * Takes as input an expression that evaluates to an n-dimensional
 * array, and a sequence of n index expressions. Returns the element
 * of the array corresponding to the values of the index expressions.
 *  
 */
public class IndexElementExpression extends AbstractExpression {

	private final AbstractExpression exp;
	private final AbstractExpression[] indices;
	
	public IndexElementExpression(AbstractExpression exp, AbstractExpression ... indices) {
		this.exp = exp;
		this.indices = indices;
	}

	@Override
	public void getVariables(Set<String> list) {
		Arrays.stream(indices).forEach(e -> e.getVariables(list));
		exp.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return exp.hasRankExpression() ||
				Arrays.stream(indices).anyMatch(e -> e.hasRankExpression());
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression[] newIndices = new AbstractExpression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (AbstractExpression)indices[i].transformRankExpressions(v, rank);
		}
		AbstractExpression newExp = (AbstractExpression)exp.transformRankExpressions(v, rank);
		IndexElementExpression iee = new IndexElementExpression(newExp, newIndices);
		iee.setLineNumber(getLineNumber());
		return iee;
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
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AbstractExpression[] newIndices = new AbstractExpression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (AbstractExpression)indices[i].replaceEmbeddedFunctionCall(fc, var);
		}
		AbstractExpression newExp = (AbstractExpression)exp.replaceEmbeddedFunctionCall(fc, var);
		IndexElementExpression iee = new IndexElementExpression(newExp, newIndices);
		iee.setLineNumber(getLineNumber());
		return iee;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		Object o = exp.getValue(e);
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
				o = String.valueOf(s.charAt(index));
			} else if (o instanceof PersistentArray) {
				PersistentArray list = (PersistentArray)o;
				int index = indices[i].getIntValue(e);
				if (index < 0 || index >= list.size()) {
					throw new RPLIndexOutOfBoundsException(index, list.size(), this);
				}
				o = list.get(index);
				if (o == null) {
					throw new RPLUndefinedException(this);
				}
			} else if (o instanceof PersistentList) {
				PersistentList<?> list = (PersistentList<?>)o;
				int index = indices[i].getIntValue(e);
				if (index < 0 || index >= list.size()) {
					throw new RPLIndexOutOfBoundsException(index, list.size(), this);
				}
				o = list.get(index);
				if (o == null) {
					throw new RPLUndefinedException(this);
				}
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
			for (AbstractExpression e: indices) {
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

	@Override
	public int hashCode() {
		return Arrays.hashCode(indices) + exp.hashCode();
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
