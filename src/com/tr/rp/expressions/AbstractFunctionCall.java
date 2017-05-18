package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.core.Expression;
import com.tr.rp.core.Function;
import com.tr.rp.core.FunctionScope;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.MultiMergeIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLFunctionUndefinedException;
import com.tr.rp.exceptions.RPLMissingReturnValueException;
import com.tr.rp.exceptions.RPLWrongNumberOfArgumentsException;
import com.tr.rp.tools.Pair;

/**
 * Function call expression. Represents a function call constructed with
 * a function name, a function scope (to retrieve the function) and a 
 * sequence of argument expressions. The arguments must match the function's
 * parameters. Otherwise an exception is thrown at runtime.
 */
public abstract class AbstractFunctionCall extends Expression {

	private final FunctionScope functionScope;
	private final String functionName;
	private final Expression[] arguments;
	
	public AbstractFunctionCall(String functionName, FunctionScope functionScope, Expression ... arguments) {
		this.functionName = functionName;
		this.functionScope = functionScope;
		this.arguments = arguments;
	}
	
	@Override
	public final boolean containsVariable(String var) {
		for (Expression arg: arguments) {
			if (arg.containsVariable(var)) {
				return true;
			}
		}
		return false;
	}

	public final String getFunctionName() {
		return functionName;
	}

	@Override
	public final boolean hasRankExpression() {
		for (Expression arg: arguments) {
			if (arg.hasRankExpression()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final AbstractFunctionCall getEmbeddedFunctionCall() {
		for (Expression arg: arguments) {
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
		if (functionScope.getFunction(functionName) == null) {
			throw new RPLFunctionUndefinedException(functionName);
		}
		return functionScope.getFunction(functionName);
	}

	public final Expression[] getArguments() {
		return arguments;
	}
	
	public abstract String toString();
	
	public abstract boolean equals(Object o);
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
	
	public abstract RankedIterator<VarStore> getIterator(Expression[] arguments, String assignToVar, RankedIterator<VarStore> parent) throws RPLException;

}
