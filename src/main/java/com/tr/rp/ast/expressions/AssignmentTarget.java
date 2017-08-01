package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.varstore.VarStore;

/**
 * An assignment target. This expression cannot be evaluated 
 * (will throw internal error) but is used to refer to the left
 * hand side of an assignment statement.
 */
public abstract class AssignmentTarget extends AbstractExpression {

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
	
	public abstract VarStore assign(VarStore vs, Object value) throws RPLException;
	
	public abstract AbstractExpression convertToRHSExpression();
	
	public abstract void getAssignedVariables(Set<String> list);

}
