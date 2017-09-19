package com.tr.rp.varstore.types;

import com.tr.rp.varstore.datastructures.PersistentStack;

public class StackType extends Type<PersistentStack<Object>> {

	StackType() {
		super("stack");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentStack;
	}

}
