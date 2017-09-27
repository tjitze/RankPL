package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.varstore.VarStore;

/**
 * Function call expression. Represents a function call constructed with
 * a function name, a function scope (to retrieve the function) and a 
 * sequence of argument expressions. The arguments must match the function's
 * parameters. Otherwise an exception is thrown at runtime.
 */
public abstract class AbstractFunctionCall extends AbstractExpression {

	private final AbstractExpression[] arguments;
	
	public AbstractFunctionCall(AbstractExpression ... arguments) {
		this.arguments = arguments;
	}


	@Override
	public final boolean hasRankExpression() {
		for (AbstractExpression arg: arguments) {
			if (arg.hasRankExpression()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final AbstractFunctionCall getEmbeddedFunctionCall() {
		for (AbstractExpression arg: arguments) {
			AbstractFunctionCall fc = arg.getEmbeddedFunctionCall();
			if (fc != null) {
				return fc;
			}
		}
		return this;
	}

	@Override
	public final void getVariables(Set<String> list) {
		for (int i = 0; i < arguments.length; i++) {
			arguments[i].getVariables(list);
		}
	}
	
	public final AbstractExpression[] getArguments() {
		return arguments;
	}
	
	public abstract String toString();
	
	public abstract boolean equals(Object o);
	
	public int hashCode() {
		return Arrays.hashCode(arguments);
	}
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
