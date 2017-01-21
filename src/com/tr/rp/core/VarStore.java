package com.tr.rp.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
	 * Initialize an array (sets all elements to 0).
	 * 
	 * @param var Array name
	 * @param size size of array
	 */
	public void initArray(String var, int size) {
		for (int i = 0; i < size; i++) {
			varStore.put(getIndexedVar(var, i), 0);
		}
	}

	/**
	 * Set element of array.
	 */
	public void setElement(String var, int index, int value) {
		setValue(getIndexedVar(var, index), value);
	}

	/**
	 * Get element of array.
	 */
	public int getElement(String var, int index) {
		return getValue(getIndexedVar(var, index));
	}
	
	/**
	 * Return array as list.
	 */
	public List<Integer> getArray(String var) {
		List<Integer> res = new ArrayList<Integer>();
		int i = 0;
		while (varStore.containsKey("_"+var+"#"+i)) {
			res.add(varStore.get("_"+var+"#"+i));
			i++;
		}
		return res;
	}

	/**
	 * @return Internal storage name used for element of array.
	 */
	public static final String getIndexedVar(String var, int index) {
		return "_"+var+"#"+index;
	}
	
	/**
	 * @return A new variable store where var is set to value.
	 */
	public VarStore create(String var, int value) {
		if (value != 0 && getValue(var) == value) return this;
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
	
}
