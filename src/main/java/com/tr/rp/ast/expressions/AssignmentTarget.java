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
