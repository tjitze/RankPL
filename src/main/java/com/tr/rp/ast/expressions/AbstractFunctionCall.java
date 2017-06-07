package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.Function;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.ranks.FunctionScope;
import com.tr.rp.varstore.VarStore;

/**
 * Function call expression. Represents a function call constructed with
 * a function name, a function scope (to retrieve the function) and a 
 * sequence of argument expressions. The arguments must match the function's
 * parameters. Otherwise an exception is thrown at runtime.
 */
public abstract class AbstractFunctionCall extends AbstractExpression {

	private final FunctionScope functionScope;
	private final String functionName;
	private final AbstractExpression[] arguments;
	
	public AbstractFunctionCall(String functionName, FunctionScope functionScope, AbstractExpression ... arguments) {
		this.functionName = functionName;
		this.functionScope = functionScope;
		this.arguments = arguments;
	}

	public final String getFunctionName() {
		return functionName;
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
	
	public final Function getFunction() throws RPLException {
		try {
			return functionScope.getFunction(functionName);
		} catch (RPLException e) {
			e.setExpression(this);
			throw e;
		}
	}

	public final AbstractExpression[] getArguments() {
		return arguments;
	}
	
	public abstract String toString();
	
	public abstract boolean equals(Object o);
	
	public int hashCode() {
		return Objects.hash(functionScope, functionName) + Arrays.hashCode(arguments);
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

	public FunctionScope getFunctionScope() {
		return functionScope;
	}
	
	public abstract RankedIterator<VarStore> getIterator(ExecutionContext c, AbstractExpression[] arguments, String assignToVar, RankedIterator<VarStore> parent) throws RPLException;

}
