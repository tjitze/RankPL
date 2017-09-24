package com.tr.rp.varstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.varstore.types.Type;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ArrayVarStore implements VarStore {

	private final HashMap<String, Integer> index;
	
	public Object[] values;
	
	private ArrayVarStore parent;
	
	protected ArrayVarStore() {
		values = new Object[0];
		parent = null;
		index = new HashMap<String, Integer>();
	}
	
	protected ArrayVarStore(Object[] values, ArrayVarStore parent, HashMap<String, Integer> index) {
		this.values = values;
		this.parent = parent;
		this.index = index;
	}

	@Override
	public <T> T getValue(String var, Type<T> asType) throws RPLUndefinedException, RPLTypeError {
		if (index.containsKey(var)) {
			Object v = values[index.get(var)];
			return asType.cast(v);
		} else {
			return null;
		}
	}

	@Override
	public Object getValue(String var) {
		if (index.containsKey(var)) {
			return values[index.get(var)];
		} else {
			return null;
		}
	}

	@Override
	public VarStore create(String var, Object value) {
		Objects.nonNull(var);
		Objects.nonNull(value);
		if (index.containsKey(var)) {
			int i = index.get(var);
			if (values[i].equals(value)) {
				return this;
			} else {
				Object[] copy = Arrays.copyOf(values, values.length);
				copy[index.get(var)] = value;
				return new ArrayVarStore(copy, parent, index);
			}
		} else {
			if (value == null) {
				return this;
			} else {
				Object[] copy = Arrays.copyOf(values, values.length + 1);
				copy[values.length] = value;
				HashMap<String, Integer> newIndex = new HashMap<String, Integer>();
				newIndex.putAll(index);
				newIndex.put(var, values.length);
				return new ArrayVarStore(copy, parent, newIndex);
			}
		}	
	}

	@Override
	public VarStore unset(String var) {
		if (index.containsKey(var)) {
			int varIndex = index.get(var);
			Object[] newValues = new Object[values.length];
			for (int i = 0; i < varIndex; i++) {
				newValues[i] = values[i];
			}
			for (int i = varIndex + 1; i < values.length; i++) {
				newValues[i - 1] = values[i];
			}
			HashMap<String, Integer> newIndex = new HashMap<String, Integer>();
			for (Entry<String, Integer> entry: newIndex.entrySet()) {
				newIndex.put(entry.getKey(), entry.getValue() >= varIndex? entry.getValue() - 1: entry.getValue());
			}
			return new ArrayVarStore(newValues, parent, newIndex);
		} else {
			return this;
		}	
	}

	@Override
	public VarStore marginalize(List<String> vars) {
		vars = new ArrayList<String>(vars);
		vars.retainAll(index.keySet().stream().map(k -> values[index.get(k)] != null).collect(Collectors.toList()));
		HashMap<String, Integer> newIndex = new HashMap<String, Integer>();
		Object[] newValues = new Object[vars.size()];
		for (int i = 0; i < vars.size(); i++) {
			newValues[i] = values[index.get(vars.get(i))];
			newIndex.put(vars.get(i), i);
		}
		return new ArrayVarStore(newValues, parent, newIndex);
	}

	@Override
	public int getSize() {
		return (int)values.length;
	}

	@Override
	public boolean containsVar(String var) {
		return index.containsKey(var) && values[index.get(var)] != null;
	}

	@Override
	public VarStore getParent() {
		return parent;
	}

	@Override
	public VarStore createClosureWith(String[] vars, List<Object> values) throws RPLException {
		throw new NotImplementedException();
	}

	@Override
	public VarStore getParentOfClosure(String target, AbstractExpression returnValueExp) throws RPLException {
		// TODO Auto-generated method stub
		return null;
	}

}
