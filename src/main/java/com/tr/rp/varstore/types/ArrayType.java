package com.tr.rp.varstore.types;

public class ArrayType extends Type<PersistentList> {

	ArrayType() {
		super("array");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentList;
	}
}
