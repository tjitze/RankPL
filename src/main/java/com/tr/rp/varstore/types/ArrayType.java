package com.tr.rp.varstore.types;

import com.tr.rp.varstore.arrays.PersistentArray;

public class ArrayType extends Type<PersistentArray> {

	ArrayType() {
		super("array");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentArray;
	}
}
