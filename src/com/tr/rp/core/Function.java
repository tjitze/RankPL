package com.tr.rp.core;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.expressions.num.NumExpression;

public class Function implements LanguageElement, FunctionScope {

	private final String name;
	private DStatement body;
	private final NumExpression returnValue;
	private final String[] parameters;
	
	public Function(String name, DStatement body, NumExpression returnValue, String ... parameters) {
		this.name = name;
		this.body = body;
		this.returnValue = returnValue;
		this.parameters = parameters;
	}

	public Function(String name, NumExpression returnValue, String ... parameters) {
		this.name = name;
		this.returnValue = returnValue;
		this.parameters = parameters;
	}

	public void setBody(DStatement body) {
		this.body = body;
	}
	
	public DStatement getBody() {
		return body;
	}

	public NumExpression getReturnValue() {
		return returnValue;
	}

	public String[] getParameters() {
		return parameters;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean containsVariable(String var) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getVariables(Set<String> list) {
		throw new UnsupportedOperationException();
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		throw new UnsupportedOperationException();
	}

	public boolean equals(Object o) {
		if (o instanceof Function) {
			Function f = (Function)o;
			return f.name.equals(name) &&
					f.body.equals(body) &&
					f.returnValue.equals(returnValue) &&
					Arrays.equals(f.parameters, parameters);
		}
		return false;
	}
	
	public String toString() {
		return name + "(" + (Arrays.stream(parameters).collect(Collectors.joining(", "))) + ")" +
					" {" + body + "; return " + returnValue + ";}";
	}

	@Override
	public Function getFunction(String name) {
		if (name.equals(name)) {
			return this;
		} else {
			throw new RuntimeException("Function " + name + " not defined");
		}
	}
}
