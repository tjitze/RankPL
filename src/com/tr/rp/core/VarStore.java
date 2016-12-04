package com.tr.rp.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class VarStore {

	public final LinkedHashMap<String, Integer> varStore = new LinkedHashMap<String, Integer>();
	
	public VarStore() {
	}
	
	public int getValue(String var) {
		if (varStore.containsKey(var)) {
			return varStore.get(var);
		}
		return 0;
	}
		
	public void setValue(String var, int value) {
		varStore.put(var, value);
	}
	
	public VarStore create(String var, int value) {
		VarStore v = new VarStore();
		v.varStore.putAll(varStore);
		v.setValue(var, value);
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
	
	public VarStore marginalize(List<String> vars) {
		VarStore v = new VarStore();
		varStore.keySet().stream()
			.filter(var -> vars.contains(var))
			.forEach(var -> v.varStore.put(var, varStore.get(var)));
		return v;
	}
}
