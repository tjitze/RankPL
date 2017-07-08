package com.tr.rp.varstore.types;

import com.tr.rp.varstore.PersistentList;

public class ArrayType extends Type<PersistentList> {

	public ArrayType() {
		super("array");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentList;
	}
}
