package com.tr.rp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;

/**
 * Represents a variable store: an assignment of values to variables.
 */
public class VarStore {

	private final LinkedHashMap<String, Object> varStore = new LinkedHashMap<String, Object>();
	private volatile static long rwCounter = 0;
	private final VarStore parent;
	
	public VarStore() {
		parent = null;
	}
	
	public VarStore(VarStore parent) {
		this.parent = parent;
	}
	
	/**
	 * @return Value of given variable (0 if not initialized).
	 */
	public Object getValue(String var) {
		return varStore.get(var);
	}
	
	public int getIntValue(String var) throws RPLException {
		Object o = getValue(var);
		if (o != null && o instanceof Integer) {
			return (Integer)o;
		} else {
			throw new RPLTypeError("integer", o);
		}
	}

	public boolean getBoolValue(String var) throws RPLException {
		Object o = getValue(var);
		if (o != null && o instanceof Boolean) {
			return (Boolean)o;
		} else {
			throw new RPLTypeError("boolean", o);
		}
	}
	
	public String getStringValue(String var) throws RPLException {
		Object o = getValue(var);
		if (o != null && o instanceof String) {
			return (String)o;
		} else {
			throw new RPLTypeError("string", o);
		}
	}
	
	/**
	 * Set value of given variable.
	 */
	public void setValue(String var, Object value) {
		varStore.put(var, value);
	}

	/**
	 * @return A new variable store where var is set to value.
	 */
	public VarStore create(String var, Object value) {
		if (getValue(var) != null && value.equals(getValue(var))) return this;
		VarStore v = new VarStore(parent);
		v.varStore.putAll(varStore);
		v.setValue(var, value);
		return v;
	}
	
	/**
	 * @return A new variable store where var is set to null (uninitialized).
	 */
	public VarStore unset(String var) {
		if (getValue(var) == null) return this;
		VarStore v = new VarStore(parent);
		v.varStore.putAll(varStore);
		v.varStore.remove(var);
		return v;
	}
	
	/**
	 * @return A marginalization of this variable store to a given set of variables.
	 */
	public VarStore marginalize(List<String> vars) {
		VarStore v = new VarStore(parent);
		varStore.keySet().stream()
			.filter(var -> vars.contains(var))
			.forEach(var -> v.varStore.put(var, varStore.get(var)));
		return v;
	}

	public boolean equals(Object o) {
		if (o instanceof VarStore) {
			return ((VarStore)o).varStore.equals(varStore);
		}
		return false;
	}
	
	public int hashCode() {
		return varStore.hashCode();
	}
	
	public String toString() {
		return
			varStore.keySet().stream()
				.sorted()
				.map(var -> var.toString() + "=" + varStore.get(var))
				.collect(Collectors.toList()).toString();
	}

	/**
	 * @return Number of assigned variables
	 */
	public int getSize() {
		return varStore.size();
	}

	/**
	 * @return True if var is assigned a value.
	 */
	public boolean containsVar(String var) {
		return varStore.containsKey(var);
	}

	public VarStore getParent() {
		return parent;
	}
	
	/**
	 * Returns a unique variable name based on the original variable name.
	 * This uses an internal static counter, whose value is included in the
	 * variable name, to avoid collisions. It is guaranteed that this method
	 * never returns the same name (except possibly in case of counter 
	 * overflow, which ¯should not occur in practice). All names returned
	 * start with "$".
	 * 
	 * @param original Original variable name
	 * @return A unique variable name based on original
	 */
	public static String getFreeVariable(String original) {
		return "$" + (rwCounter++) + "_" + original;
	}
	
	public VarStore createClosure(String[] parameters, Expression[] arguments) throws RPLException {
		if (parameters.length != arguments.length) {
			throw new InternalError("Wrong number of arguments");
		}
		VarStore v = new VarStore(this);
		for (int i = 0; i < parameters.length; i++) {
			String var = parameters[i];
			Expression expr = arguments[i];
			v.setValue(var, expr.getValue(this));
		}
		return v;
	}
	
	public VarStore getParentOfClosure(String functionValueVar, Expression returnValueExp) throws RPLException {
		if (parent == null) {
			throw new InternalError("Missing parent scope");
		}
		Object value = returnValueExp.getValue(this);
		return parent.create(functionValueVar, value);
	}
}
