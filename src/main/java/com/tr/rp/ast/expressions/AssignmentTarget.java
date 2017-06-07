package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.varstore.PersistentList;
import com.tr.rp.varstore.VarStore;

/**
 * An assignment target. This expression cannot be evaluated 
 * (will throw internal error) but is used to refer to the left
 * hand side of an assignment statement. It consists of a variable
 * and an optional list of indices.
 */
public class AssignmentTarget extends AbstractExpression {

	private final String name;
	private final AbstractExpression[] indices;
	
	public AssignmentTarget(String name, AbstractExpression ... indices) {
		this.name = name;
		this.indices = indices;
	}

	public String getAssignedVariable() {
		return name;
	}
	
	public AbstractExpression[] getIndices() {
		return indices;
	}
	
	@Override
	public void getVariables(Set<String> list) {
		Arrays.stream(indices).forEach(e -> e.getVariables(list));
		list.add(name);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		AbstractExpression[] newIndices = new AbstractExpression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (AbstractExpression)indices[i].replaceVariable(a, b);
		}
		return new AssignmentTarget(a.equals(name)? b: name);
	}

	@Override
	public boolean hasRankExpression() {
		return Arrays.stream(indices).anyMatch(e -> e.hasRankExpression());
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression[] newIndices = new AbstractExpression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (AbstractExpression)indices[i].transformRankExpressions(v, rank);
		}
		return new AssignmentTarget(name, newIndices);
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
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AbstractExpression[] newIndices = new AbstractExpression[indices.length];
		for (int i = 0; i < indices.length; i++) {
			newIndices[i] = (AbstractExpression)indices[i].replaceEmbeddedFunctionCall(fc, var);
		}
		return new AssignmentTarget(name);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		throw new RPLMiscException("Internal error: Attempt to evaluate left-hand side of assignment statement.");
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		throw new RPLMiscException("Internal error: Attempt to evaluate left-hand side of assignment statement.");
	}
	
	public String toString() {
		String indexString = "";
		if (indices.length > 0) {
			for (AbstractExpression e: indices) {
				indexString += "[" + e + "]";
			}
		}
		return name + indexString;
	}
	
	public boolean equals(Object o) {
		return (o instanceof AssignmentTarget) &&
				((AssignmentTarget)o).name.equals(name) &&
				Arrays.deepEquals(((AssignmentTarget)o).indices, indices);
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
			return vs.create(name, mutateList(0, vs.getListValue(name), value, vs));
		}
	}
	
	private PersistentList mutateList(int i, PersistentList in, Object value, VarStore vs) throws RPLException {
		PersistentList list = (PersistentList)in;
		int position = indices[i].getIntValue(vs);
		if (position < 0 || position >= list.size()) {
			throw new RPLIndexOutOfBoundsException(position, list.size(), this);
		}
		if (i == indices.length - 1) {
			return list.getMutatedCopy(position, value);
		} else {
			Object o = list.get(position);
			if (o != null && o instanceof PersistentList) {
				return list.getMutatedCopy(position, mutateList(i + 1, (PersistentList)o, value, vs));
			} else if (o == null) {
				throw new RPLUndefinedException(this);
			} else {
				throw new RPLTypeError("list", o, this);
			}
		}
	}
	
	/**
	 * @return Right-hand side version of this expression
	 */
	public AbstractExpression convertToRHSExpression() {
		Variable v = new Variable(name);
		if (indices.length > 0) {
			return new IndexElementExpression(v, indices);
		} else {
			return v;
		}
	}

}
