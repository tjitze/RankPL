package com.tr.rp.varstore;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.varstore.types.Type;

import io.usethesource.capsule.Map;
import io.usethesource.capsule.core.PersistentTrieMap;

public final class TrieMapVarStore implements VarStore {
	
	private final Map.Immutable<String, Object> varStore;
	private final TrieMapVarStore parent;

	public TrieMapVarStore() {
		varStore = PersistentTrieMap.of();
		parent = null;
	}
	
	public TrieMapVarStore(TrieMapVarStore parent) {
		varStore  = PersistentTrieMap.of();
		this.parent = parent;
	}
	
	private TrieMapVarStore(TrieMapVarStore previous, String var, Object value) {
		varStore = value == null? 
				previous.varStore.__remove(var)
				: previous.varStore.__put(var, value);
		this.parent = previous.parent;
	}

	public TrieMapVarStore(TrieMapVarStore previous, TrieMapVarStore parent, ImmutableMap<String, Object> assign, ImmutableList<String> remove) {
		Map.Immutable<String, Object> newVarStore = previous.varStore.__putAll(assign);
		for (String rem: remove) {
			newVarStore = newVarStore.__remove(rem);
		}
		varStore = newVarStore;
		this.parent = parent;
	}

	public <T> T getValue(String var, Type<T> asType) throws RPLUndefinedException, RPLTypeError {
		Object o = getValue(var);
		if (o == null) {
			throw new RPLUndefinedException(var);
		}
		if (!asType.test(o)) {
			throw new RPLTypeError(asType.getName(), o, new Variable(var));
		}
		return asType.cast(o);
	}

	public Object getValue(String var) {
		return varStore.get(var);
	}
	
	public VarStore create(String var, Object value) {
		return new TrieMapVarStore(this, var, value);
	}
	
	/**
	 * @return A new variable store where var is set to null (uninitialized).
	 */
	public VarStore unset(String var) {
		if (getValue(var) == null) return this;
		return new TrieMapVarStore(this, var, null);
	}
	
	/**
	 * @return A marginalization of this variable store to a given set of variables.
	 */
	public VarStore marginalize(List<String> vars) {
		ImmutableList.Builder<String> remove = ImmutableList.builder();
		for (String var: varStore.keySet()) {
			if (!vars.contains(var)) {
				remove.add(var);
			}
		}
		return new TrieMapVarStore(this, this.parent, ImmutableMap.of(), remove.build());
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof TrieMapVarStore) {
			return varStore.equals(((TrieMapVarStore)o).varStore);
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
	
	public VarStore createClosureWith(String[] vars, List<Object> values) throws RPLException {
		if (vars.length != values.size()) {
			throw new IllegalArgumentException();
		}
		// Create new var store with parameters
		ImmutableMap.Builder<String, Object> assign = ImmutableMap.builder();
		ImmutableList.Builder<String> remove = ImmutableList.builder();
		for (int x = 0; x < vars.length; x++) {
			if (values.get(x) != null) {
				assign.put(vars[x], values.get(x));
			} else {
				remove.add(vars[x]);
			}
		}
		return new TrieMapVarStore(this, this, assign.build(), remove.build());
	}
	
	public VarStore getParentOfClosure(String target, AbstractExpression returnValueExp) throws RPLException {
		if (parent == null) {
			throw new InternalError("Missing parent scope");
		}
		Object value = returnValueExp.getValue(this);
		return parent.create(target, value);
	}
}
