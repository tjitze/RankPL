package com.tr.rp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tr.rp.expressions.num.NumExpression;

/**
 * Represents a variable store: an assignment of values to variables.
 */
public class VarStore {

	private final LinkedHashMap<String, Integer> varStore = new LinkedHashMap<String, Integer>();
	
	/**
	 * @return Value of given variable (0 if not initialized).
	 */
	public int getValue(String var) {
		if (varStore.containsKey(var)) {
			return varStore.get(var);
		}
		return 0;
	}
	
	/**
	 * Set value of given variable.
	 */
	public void setValue(String var, int value) {
		varStore.put(var, value);
	}

	/**
	 * @return A new variable store where var is set to value.
	 */
	public VarStore create(String var, int value) {
		if (getValue(var) == value) return this;
		VarStore v = new VarStore();
		v.varStore.putAll(varStore);
		v.setValue(var, value);
		return v;
	}
	
	/**
	 * @return A marginalization of this variable store to a given set of variables.
	 */
	public VarStore marginalize(List<String> vars) {
		VarStore v = new VarStore();
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

}
