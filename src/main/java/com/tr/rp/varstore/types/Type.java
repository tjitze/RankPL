package com.tr.rp.varstore.types;

import com.google.common.collect.ImmutableList;
import com.tr.rp.varstore.arrays.PersistentArray;
import com.tr.rp.varstore.datastructures.PersistentList;
import com.tr.rp.varstore.datastructures.PersistentMap;
import com.tr.rp.varstore.datastructures.PersistentSet;
import com.tr.rp.varstore.datastructures.PersistentStack;

public class Type<T> {

	private final String name;
	private final Class<T> c;
	
	private static <T> Type<T> create(String name, Class<T> c) {
		return new Type<T>(name, c);
	}
	
	private Type(String name, Class<T> c) {
		this.name = name;
		this.c = c;
	}
	
	@SuppressWarnings("unchecked")
	public T cast(Object o) {
		return (T)o;
	}
	
	public boolean test(Object o) {
		return c.isInstance(o);
	}
	
	public String getName() {
		return name;
	}
	
	public static final Type<Integer> INT = Type.create("int", Integer.class);
	public static final Type<Boolean> BOOL = Type.create("boolean", Boolean.class);
	public static final Type<PersistentArray> ARRAY = Type.create("array", PersistentArray.class);
	public static final Type<String> STRING = Type.create("string", String.class);
	public static final Type<PersistentStack> STACK = Type.create("stack", PersistentStack.class);
	public static final Type<PersistentSet> SET = Type.create("set", PersistentSet.class);
	public static final Type<PersistentMap> MAP = Type.create("map", PersistentMap.class);
	public static final Type<PersistentList> LIST = Type.create("list", PersistentList.class);
	
	public static final ImmutableList<Type<?>> ALL_TYPES 
		= ImmutableList.of(INT, BOOL, ARRAY, STRING, STACK, SET, MAP, LIST);
}
