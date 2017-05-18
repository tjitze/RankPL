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

/**
 * An indexed variable. Constructed with a variable name and a sequence of 
 * expressions that represent (possibly multi-dimensional) array indices. 
 * This expression evaluates to the value of the value stored at the given
 * index. A type error is thrown if this does not refer to an array element.
 */
public class Variable extends Expression {

	private final String name;
	private final Expression[] indices;
	
	public Variable(String name, Expression ... indices) {
		this.name = name;
		this.indices = indices;
	}
	
	@Override
	public boolean containsVariable(String var) {
		return Arrays.stream(indices).anyMatch(e -> e.containsVariable(var)) || var.equals(name);
	}

	@Override
	public void getVariables(Set<String> list) {
		Arrays.stream(indices).forEach(e -> e.getVariables(list));
		list.add(name);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		Expression[] newIndices = new Expression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (Expression)indices[i].replaceVariable(a, b);
		}
		return new Variable(a.equals(name)? b: name, newIndices);
	}

	@Override
	public boolean hasRankExpression() {
		return Arrays.stream(indices).anyMatch(e -> e.hasRankExpression());
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		Expression[] newIndices = new Expression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (Expression)indices[i].transformRankExpressions(v, rank);
		}
		return new Variable(name, newIndices);
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		for (int i = 0; i < indices.length; i++) {
			AbstractFunctionCall fc = indices[i].getEmbeddedFunctionCall();
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
		return new Variable(name, newIndices);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		Object o = e.getValue(name);
		for (int i = 0; i < indices.length; i++) {
			if (o == null) {
				throw new RPLTypeError("list", o);
			} else if (o instanceof String) {
				// Allow strings to be referenced as 1D array
				String s = (String)o;
				int index = indices[i].getIntValue(e);
				if (index < 0 || index >= s.length()) {
					throw new RPLIndexOutOfBoundsException(index, s.length());
				}
				o = s.substring(index, index + 1);
			} else if (o instanceof PersistentList) {
				PersistentList list = (PersistentList)o;
				int index = indices[i].getIntValue(e);
				if (index < 0 || index >= list.size()) {
					throw new RPLIndexOutOfBoundsException(index, list.size());
				}
				o = list.get(index);
			} else {
				throw new RPLTypeError("list", o);
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
		return name + indexString;
	}
	
	public boolean equals(Object o) {
		return (o instanceof Variable) &&
				((Variable)o).name.equals(name) &&
				Arrays.deepEquals(((Variable)o).indices, indices);
	}

	/**
	 * Assign given value to this variable in the given variable store. 
	 * Return the mutated variable store.
	 * 
	 * @param item Initial variable store
	 * @param value Value to assign
	 * @return Mutated variable store
	 */
	public VarStore assign(VarStore vs, Object value) throws RPLException {
		if (indices.length == 0) {
			return vs.create(name, value);
		} else {
			return vs.create(name, mutateList(0, vs.getValue(name), value, vs));
		}
	}
	
	private PersistentList mutateList(int i, Object in, Object value, VarStore vs) throws RPLException {
		if (in == null) {
			throw new RPLTypeError("list", in);
		} else if (in instanceof PersistentList) {
			PersistentList list = (PersistentList)in;
			int position = indices[i].getIntValue(vs);
			if (position < 0 || position >= list.size()) {
				throw new RPLIndexOutOfBoundsException(position, list.size());
			}
			if (i == indices.length - 1) {
				return list.getMutatedCopy(position, value);
			} else {
				return list.getMutatedCopy(position, mutateList(i + 1, list.get(position), value, vs));
			}
		} else {
			throw new RPLTypeError("list", in);
		}
	}

}
