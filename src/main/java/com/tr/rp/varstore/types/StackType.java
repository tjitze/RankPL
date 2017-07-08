package com.tr.rp.varstore.types;

public class StackType extends Type<PersistentStack<Object>> {

	StackType() {
		super("stack");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentStack;
	}

}
