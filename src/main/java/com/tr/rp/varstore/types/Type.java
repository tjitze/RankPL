package com.tr.rp.varstore.types;

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
	public static final Type<PersistentList> ARRAY = new ArrayType();
	public static final Type<String> STRING = new StringType();
	public static final Type<PersistentStack<Object>> STACK = new StackType();
}
