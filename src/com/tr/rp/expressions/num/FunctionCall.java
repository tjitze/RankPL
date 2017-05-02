package com.tr.rp.expressions.num;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.core.Function;
import com.tr.rp.core.FunctionScope;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;

public class FunctionCall extends NumExpression {

	private final FunctionScope functionScope;
	private final String functionName;
	private final NumExpression[] arguments;
	
	public FunctionCall(String functionName, FunctionScope functionScope, NumExpression ... arguments) {
		this.functionName = functionName;
		this.functionScope = functionScope;
		this.arguments = arguments;
	}
	
	@Override
	public boolean containsVariable(String var) {
		for (NumExpression arg: arguments) {
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
		NumExpression[] newArgs = new NumExpression[arguments.length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (NumExpression)arguments[i].replaceVariable(a, b);
		}
		return new FunctionCall(functionName, functionScope, newArgs);
	}

	@Override
	public int getVal(VarStore e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public int getDefiniteValue() {
		return 0;
	}

	@Override
	public boolean hasRankExpression() {
		for (NumExpression arg: arguments) {
			if (arg.hasRankExpression()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		NumExpression[] newArgs = new NumExpression[arguments.length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (NumExpression)arguments[i].transformRankExpressions(v, rank);
		}
		return new FunctionCall(functionName, functionScope, newArgs);
	}

	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		for (NumExpression arg: arguments) {
			FunctionCall fc = arg.getEmbeddedFunctionCall();
			if (fc != null) {
				return fc;
			}
		}
		return this;
	}

	@Override
	public NumExpression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		if (fc == this) {
			return new Var(var);
		} else {
			NumExpression[] newArgs = new NumExpression[arguments.length];
			for (int i = 0; i < newArgs.length; i++) {
				newArgs[i] = (NumExpression)arguments[i].replaceEmbeddedFunctionCall(fc, var);
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
	
	public Function getFunction() {
		return functionScope.getFunction(functionName);
	}

	public NumExpression[] getArguments() {
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
}
