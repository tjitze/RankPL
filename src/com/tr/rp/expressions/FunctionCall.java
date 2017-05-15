package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.core.Expression;
import com.tr.rp.core.Function;
import com.tr.rp.core.FunctionScope;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLFunctionUndefinedException;

/**
 * Function call expression. Represents a function call constructed with
 * a function name, a function scope (to retrieve the function) and a 
 * sequence of argument expressions. The arguments must match the function's
 * parameters. Otherwise an exception is thrown at runtime.
 */
public class FunctionCall extends Expression {

	private final FunctionScope functionScope;
	private final String functionName;
	private final Expression[] arguments;
	
	public FunctionCall(String functionName, FunctionScope functionScope, Expression ... arguments) {
		this.functionName = functionName;
		this.functionScope = functionScope;
		this.arguments = arguments;
	}
	
	@Override
	public boolean containsVariable(String var) {
		for (Expression arg: arguments) {
			if (arg.containsVariable(var)) {
				return true;
			}
		}
		return false;
	}

	public String getFunctionName() {
		return functionName;
	}
	
	@Override
	public LanguageElement replaceVariable(String a, String b) {
		Expression[] newArgs = new Expression[arguments.length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (Expression)arguments[i].replaceVariable(a, b);
		}
		return new FunctionCall(functionName, functionScope, newArgs);
	}

	@Override
	public boolean hasRankExpression() {
		for (Expression arg: arguments) {
			if (arg.hasRankExpression()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		Expression[] newArgs = new Expression[arguments.length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (Expression)arguments[i].transformRankExpressions(v, rank);
		}
		return new FunctionCall(functionName, functionScope, newArgs);
	}

	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		for (Expression arg: arguments) {
			FunctionCall fc = arg.getEmbeddedFunctionCall();
			if (fc != null) {
				return fc;
			}
		}
		return this;
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		if (fc == this) {
			return new Variable(var);
		} else {
			Expression[] newArgs = new Expression[arguments.length];
			for (int i = 0; i < newArgs.length; i++) {
				newArgs[i] = (Expression)arguments[i].replaceEmbeddedFunctionCall(fc, var);
			}
			return new FunctionCall(functionName, functionScope, newArgs);
		}
	}

	@Override
	public void getVariables(Set<String> list) {
		for (int i = 0; i < arguments.length; i++) {
			arguments[i].getVariables(list);
		}
	}
	
	public Function getFunction() throws RPLException {
		if (functionScope.getFunction(functionName) == null) {
			throw new RPLFunctionUndefinedException(functionName);
		}
		return functionScope.getFunction(functionName);
	}

	public Expression[] getArguments() {
		return arguments;
	}
	
	public String toString() {
		return functionName + "(" + 
				Arrays.stream(arguments).map(e -> e.toString()).collect(Collectors.joining(", ")) + ")";
	}
	
	public boolean equals(Object o) {
		if (o instanceof FunctionCall) {
			return ((FunctionCall)o).functionName.equals(functionName) &&
					Arrays.equals(((FunctionCall)o).arguments, arguments);
		}
		return false;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
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
}
