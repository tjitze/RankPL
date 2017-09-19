package com.tr.rp.varstore.types;

import com.tr.rp.varstore.arrays.Array;
import com.tr.rp.varstore.datastructures.MapType;
import com.tr.rp.varstore.datastructures.PersistentList;
import com.tr.rp.varstore.datastructures.PersistentMap;
import com.tr.rp.varstore.datastructures.PersistentSet;
import com.tr.rp.varstore.datastructures.PersistentStack;

public abstract class Type<T> {

	private final String name;
	
	protected Type(String name) {
		this.name = name;
	}
	
	@SuppressWarnings("unchecked")
	public T cast(Object o) {
		return (T)o;
	}
	
	public abstract boolean test(Object o);
	
	public String getName() {
		return name;
	}
	
	public static final Type<Integer> INT = new IntType();
	public static final Type<Boolean> BOOL = new BoolType();
	public static final Type<Array> ARRAY = new ArrayType();
	public static final Type<String> STRING = new StringType();
	public static final Type<PersistentStack<Object>> STACK = new StackType();
	public static final Type<PersistentSet<Object>> SET = new SetType();
	public static final Type<PersistentMap<Object, Object>> MAP = new MapType();
	public static final Type<PersistentList<Object>> LIST = new ListType();
}
