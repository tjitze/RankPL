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
		if (value != 0 && getValue(var) == value) return this;
		VarStore v = new VarStore();
		v.varStore.putAll(varStore);
		v.setValue(var, value);
		return v;
	}
	
	public VarStore create(String varName, NumExpression[] indexedValues) {
		if (indexedValues.length % 2 != 0) throw new IllegalArgumentException("Even number of index/value elements required");
		VarStore v = new VarStore();
		v.varStore.putAll(varStore);
		for (int i = 0; i < indexedValues.length; i = i + 2) {
			v.setElementOfArray(varName, indexedValues[i].getVal(this), indexedValues[i+1].getVal(this));
		}
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
	 * Get element of n-dimensional array, where n equals the number of
	 * provided index arguments.
	 */
	public Optional<Integer> getElementOfArray(String varName, int ... index) {
		for (int i: index) varName += "#" + i;
		if (varStore.containsKey(varName)) {
			return Optional.of(varStore.get(varName));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Set element of n-dimensional array, where n equals the number of 
	 * provided index arguments.
	 */
	public void setElementOfArray(String varName, int value, int ... index) {
		for (int i: index) varName += "#" + i;
		varStore.put(varName, value);
	}
	
	/**
	 * Initialize array. The dimension array contains the number of
	 * items that must be initialized for each dimension.
	 */
	public void initializeArray(String varName, int[] dimension) {
		dimension = Arrays.copyOf(dimension, dimension.length);
		for (int cx = 0; cx < dimension.length; cx++) {
			while (dimension[cx] > 0) {
				dimension[cx]--;
				setElementOfArray(varName, 0, dimension);
			}
		}
	}
	
	public int[] get1DArray(String varName, int length) {
		int[] res = new int[length];
		for (int i = 0; i < length; i++) {
			res[i] = getElementOfArray(varName, i)
					.orElseThrow(() -> new IndexOutOfBoundsException());
		}
		return res;
	}

	public int[][] get2DArray(String varName, int length1, int length2) {
		int[][] res = new int[length1][length2];
		for (int i = 0; i < length1; i++) {
			for (int j = 0; j < length2; j++) {
				res[i][j] = getElementOfArray(varName, i, j)
						.orElseThrow(() -> new IndexOutOfBoundsException());
			}
		}
		return res;
	}


}
