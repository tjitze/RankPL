package com.tr.rp.ast.expressions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.varstore.VarStore;

public abstract class AbstractFunctionCall extends AbstractExpression {

	@Override
	public final Object getValue(VarStore e) throws RPLException {
		throw new RuntimeException("Illegal operation (evaluating untransformed function call)");
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return null;
	}
	
	public abstract Executor getExecutor(ExecutionContext c, String assignToVar, Executor out);

}
