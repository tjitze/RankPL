package com.tr.rp.core;

import java.util.HashMap;

public class VarStore {

	public final HashMap<String, Integer> varStore = new HashMap<String, Integer>();
	
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
		return varStore.toString();
	}
	
}
