package com.tr.rp.varstore;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.organicdesign.fp.collections.PersistentTreeMap;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.varstore.types.Type;

/**
 * Represents a variable store: an assignment of values to variables.
 */
public class PTMVarStore implements VarStore {

	private PersistentTreeMap<String, Object> varStore;
	private final PTMVarStore parent;


	// Hash code caching
	private int hashCode = 0;
	private boolean hashCodeComputed = false;
	
	public PTMVarStore() {
		varStore = PersistentTreeMap.empty();
		parent = null;
	}
	
	public PTMVarStore(PTMVarStore parent) {
		varStore  = PersistentTreeMap.empty();
		this.parent = parent;
	}
	
	private PTMVarStore(PTMVarStore previous, String var, Object value) {
		if (value != null) {
			this.varStore = previous.varStore.assoc(var, value);
		} else {
			this.varStore = previous.varStore.without(var);
		}
		this.parent = previous.parent;
	}

	/**
	 * @return Value of given variable (0 if not initialized).
	 */
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

	/**
	 * @return Value of given variable (0 if not initialized).
	 */
	public Object getValue(String var) {
		return varStore.get(var);
	}
	
	/**
	 * Mutates this variable store. Returns a new variable store where the
	 * given variable is set to the given value, without changing the
	 * original variable store. Using a null value will un-set the variable.
	 * 
	 * @return A new variable store where var is set to value.
	 */
	public VarStore create(String var, Object value) {
		if (Objects.equals(varStore.get(var), value)) {
			return this;
		}
		return new PTMVarStore(this, var, value);
	}
	
	/**
	 * Internal method to set value of variable.
     *
	 * @param var name of variable to set
	 * @param value the value
	 */
	protected void setValue(String var, Object value) {
		if (value == null) {
			varStore = varStore.without(var);
		} else {
			varStore = varStore.assoc(var, value);
		}
	}
	
	/**
	 * @return A new variable store where var is set to null (uninitialized).
	 */
	public VarStore unset(String var) {
		if (getValue(var) == null) return this;
		return new PTMVarStore(this, var, null);
	}
	
	/**
	 * @return A marginalization of this variable store to a given set of variables.
	 */
	public VarStore marginalize(List<String> vars) {
		PTMVarStore v = new PTMVarStore(parent);
		for (String var: vars) {
			Object value = varStore.get(var);
			if (value != null) {
				v = (PTMVarStore)v.create(var, value);
			}
		}
		return v;
	}

	public boolean equals(Object o) {
		if (o instanceof PTMVarStore) {
			PTMVarStore other = (PTMVarStore)o;
			return Objects.equals(parent, other.parent)
					&& Objects.equals(varStore, other.varStore);
		}
		return false;
	}
	
	public int hashCode() {
		synchronized (this) {
			if (!hashCodeComputed) {
				hashCode = varStore.hashCode() + (parent != null? parent.hashCode(): 0);
				hashCodeComputed = true;
			}
		}
		return hashCode;
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
		PTMVarStore v = new PTMVarStore(this);
		for (int x = 0; x < vars.length; x++) {
			v = new PTMVarStore(v, vars[x], values.get(x));
		}
		return v;
	}
	
	public VarStore getParentOfClosure(String target, AbstractExpression returnValueExp) throws RPLException {
		if (parent == null) {
			throw new InternalError("Missing parent scope");
		}
		Object value = returnValueExp.getValue(this);
		return parent.create(target, value);
	}
}
