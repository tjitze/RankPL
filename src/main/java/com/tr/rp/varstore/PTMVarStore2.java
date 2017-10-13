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
public class PTMVarStore2 implements VarStore {

	private PersistentTreeMap<String, Object> varStore;
	private final PTMVarStore2 parent;
	private Object i;
	private Object j;
	private Object k;

	// Hash code caching
	private int hashCode = 0;
	private boolean hashCodeComputed = false;
	
	protected PTMVarStore2() {
		varStore = PersistentTreeMap.empty();
		parent = null;
		i = 0;
		j = 0;
		k = 0;
	}
	
	public PTMVarStore2(PTMVarStore2 parent) {
		varStore  = PersistentTreeMap.empty();
		this.parent = parent;
		i = 0;
		j = 0;
		k = 0;
	}
	
	private PTMVarStore2(PTMVarStore2 previous, String var, Object value, Object i, Object j, Object k) {
		if (value != null) {
			this.varStore = previous.varStore.assoc(var, value);
		} else {
			this.varStore = previous.varStore.without(var);
		}
		this.parent = previous.parent;
		this.i = i;
		this.j = j;
		this.k = k;
	}

	private PTMVarStore2(PTMVarStore2 previous, Object i, Object j, Object k) {
		this.varStore = previous.varStore;
		this.parent = previous.parent;
		this.i = i;
		this.j = j;
		this.k = k;
	}

	/**
	 * @return Value of given variable (0 if not initialized).
	 */
	public <T> T getValue(String var, Type<T> asType) throws RPLUndefinedException, RPLTypeError {
		if (asType == Type.INT && var.equals("i")) {
			return (T) i;
		}
		if (asType == Type.INT && var.equals("j")) {
			return (T) j;
		}
		if (asType == Type.INT && var.equals("k")) {
			return (T) k;
		}
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
		if (var.equals("i")) {
			return i;
		}
		if (var.equals("j")) {
			return j;
		}
		if (var.equals("k")) {
			return k;
		}
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
		if (var.equals("i")) {
			return new PTMVarStore2(this, (int)value, j, k);
		}
		if (var.equals("j")) {
			return new PTMVarStore2(this, i, (int)value, k);
		}
		if (var.equals("k")) {
			return new PTMVarStore2(this, i, j, (int)value);
		}		
		if (Objects.equals(varStore.get(var), value)) {
			return this;
		}
		return new PTMVarStore2(this, var, value, i, j, k);
	}
	
	/**
	 * Internal method to set value of variable.
     *
	 * @param var name of variable to set
	 * @param value the value
	 */
	protected void setValue(String var, Object value) {
		if (var.equals("i")) {
			this.i = (Integer)value;
		}
		if (var.equals("j")) {
			this.j = (Integer)value;
		}
		if (var.equals("k")) {
			this.k = (Integer)value;
		}		
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
		return new PTMVarStore2(this, var, null, i, j, k);
	}
	
	/**
	 * @return A marginalization of this variable store to a given set of variables.
	 */
	public VarStore marginalize(List<String> vars) {
		PTMVarStore2 v = new PTMVarStore2(parent);
		for (String var: vars) {
			Object value;
			if (var.equals("i")) {
				value = i;
			} if (var.equals("j")) {
				value = j;
			} else if (var.equals("k")) {
				value = k;
			} else {
				value = varStore.get(var);
			}
			if (value != null) {
				v = (PTMVarStore2)v.create(var, value);
			}
		}
		return v;
	}

	public boolean equals(Object o) {
		if (o instanceof PTMVarStore2) {
			PTMVarStore2 other = (PTMVarStore2)o;
			return Objects.equals(parent, other.parent)
					&& Objects.equals(varStore, other.varStore)
					&& Objects.equals(i, other.i) 
					&& Objects.equals(j, other.j) 
					&& Objects.equals(k, other.k);
		}
		return false;
	}
	
	public int hashCode() {
		synchronized (this) {
			if (!hashCodeComputed) {
				hashCode = varStore.hashCode() + (parent != null? parent.hashCode(): 0)
						+ (i == null? 0: i.hashCode())
						+ (j == null? 0: j.hashCode())
						+ (k == null? 0: k.hashCode());

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
		if (vars.length != vars.length) {
			throw new InternalError("Wrong number of arguments");
		}
		// Create new var store with parameters
		PTMVarStore2 v = new PTMVarStore2(this);
		for (int x = 0; x < vars.length; x++) {
			String var = vars[x];
			v = new PTMVarStore2(v, var, values.get(x), i, j, k);
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
