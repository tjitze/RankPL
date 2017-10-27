package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLWrongAssignmentTargetSize;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.arrays.PersistentArray;
import com.tr.rp.varstore.arrays.PersistentObjectArray;
import com.tr.rp.varstore.types.Type;

public class AssignmentTargetList extends AssignmentTarget {

	private final AssignmentTarget[] elements;
	
	public AssignmentTargetList(AssignmentTarget[] elements) {
		this.elements = elements;
	}
	
	@Override
	public void getVariables(Set<String> list) {
		Arrays.stream(elements).forEach(e -> e.getVariables(list));
	}

	@Override
	public void getAssignedVariables(Set<String> list) {
		Arrays.stream(elements).forEach(e -> e.getAssignedVariables(list));
	}

	@Override
	public boolean hasRankExpression() {
		return Arrays.stream(elements).anyMatch(e -> e.hasRankExpression());
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AssignmentTarget[] newElements = new AssignmentTarget[elements.length];
		for (int i = 0; i < elements.length; i++) {
			newElements[i] = (AssignmentTarget)elements[i].transformRankExpressions(v, rank);
		}
		AssignmentTargetList a = new AssignmentTargetList(newElements);
		a.setLineNumber(getLineNumber());
		return a;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		for (int i = 0; i < elements.length; i++) {
			AbstractFunctionCall fc = elements[i].getEmbeddedFunctionCall();
			if (fc != null) {
				return fc;
			}
		}
		return null;
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AssignmentTarget[] newElements = new AssignmentTarget[elements.length];
		for (int i = 0; i < elements.length; i++) {
			newElements[i] = (AssignmentTarget)elements[i].replaceEmbeddedFunctionCall(fc, var);
		}
		AssignmentTargetList a = new AssignmentTargetList(newElements);
		a.setLineNumber(getLineNumber());
		return a;
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
		return "[" + Arrays.stream(elements).map(Object::toString).collect(Collectors.joining(", ")) + "]";
	}
	
	public boolean equals(Object o) {
		return (o instanceof AssignmentTargetList) &&
				Arrays.deepEquals(((AssignmentTargetList)o).elements, elements);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode() + Arrays.hashCode(elements);
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
		PersistentArray values = null;
		try {
			values = Type.ARRAY.cast(value);
		} catch (ClassCastException e) {
			throw new RPLTypeError("Array of length " + elements.length, value.getClass(), this);
		}
		if (values.size() != elements.length) {
			throw new RPLWrongAssignmentTargetSize(elements.length, values.size(), this);
		}
		for (int i = 0; i < values.size(); i++) {
			value = values.get(i);
			AssignmentTarget target = elements[i];
			vs = target.assign(vs, value);
		}
		return vs;
	}
	
	/**
	 * @return Right-hand side version of this expression
	 */
	public AbstractExpression convertToRHSExpression() {
		AbstractExpression[] values = new AbstractExpression[elements.length];
		for (int i = 0; i < elements.length; i++) {
			values[i] = elements[i].convertToRHSExpression();
		}
		ArrayConstructExpression a = new ArrayConstructExpression(values);
		a.setLineNumber(getLineNumber());
		return a;
	}

}
