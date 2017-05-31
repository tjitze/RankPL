package com.tr.rp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.organicdesign.fp.collections.PersistentTreeMap;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.expressions.AssignmentTarget;

/**
 * Represents a variable store: an assignment of values to variables.
 */
public class VarStore {

	private PersistentTreeMap<String, Object> varStore;
	private volatile static long rwCounter = 0;
	private final VarStore parent;
	
	public VarStore() {
		varStore = PersistentTreeMap.empty();
		parent = null;
	}
	
	public VarStore(VarStore parent) {
		varStore  = PersistentTreeMap.empty();
		this.parent = parent;
	}
	
	private VarStore(VarStore previous, String var, Object value) {
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
	public Object getValue(String var) {
		return varStore.get(var);
	}
	
	/**
	 * Get integer value of variable
	 * 
	 * @param var Name of variable to get
	 * @return Integer value of variable
	 * @throws RPLException If variable is undefined or not an integer
	 */
	public int getIntValue(String var) throws RPLException {
		Object o = getValue(var);
		if (o != null && o instanceof Integer) {
			return (Integer)o;
		} else if (o == null) {
			throw new RPLUndefinedException(var);
		} else {
			throw new RPLTypeError("integer", o);
		}
	}

	/**
	 * Get boolean value of variable
	 * 
	 * @param var Name of variable to get
	 * @return Boolean value of variable
	 * @throws RPLException If variable is undefined or not boolean
	 */
	public boolean getBoolValue(String var) throws RPLException {
		Object o = getValue(var);
		if (o != null && o instanceof Boolean) {
			return (Boolean)o;
		} else if (o == null) {
			throw new RPLUndefinedException(var);
		} else {
			throw new RPLTypeError("boolean", o);
		}
	}
	
	/**
	 * Get String value of variable
	 * 
	 * @param var Name of variable to get
	 * @return String value of variable
	 * @throws RPLException If variable is undefined or not String
	 */
	public String getStringValue(String var) throws RPLException {
		Object o = getValue(var);
		if (o != null && o instanceof String) {
			return (String)o;
		} else if (o == null) {
			throw new RPLUndefinedException(var);
		} else {
			throw new RPLTypeError("string", o);
		}
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
		return new VarStore(this, var, value);
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
		return new VarStore(this, var, null);
	}
	
	/**
	 * @return A marginalization of this variable store to a given set of variables.
	 */
	public VarStore marginalize(List<String> vars) {
		VarStore v = new VarStore(parent);
		for (String var: vars) {
			Object value = varStore.get(var);
			if (value != null) {
				v = v.create(var, value);
			}
		}
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
		// Create new var store with parameters
		VarStore v = new VarStore(this);
		for (int i = 0; i < parameters.length; i++) {
			String var = parameters[i];
			Expression expr = arguments[i];
			v = new VarStore(v, var, expr.getValue(this));
		}
		return v;
	}
	
	public VarStore getParentOfClosure(String target, Expression returnValueExp) throws RPLException {
		if (parent == null) {
			throw new InternalError("Missing parent scope");
		}
		Object value = returnValueExp.getValue(this);
		return parent.create(target, value);
	}
}
