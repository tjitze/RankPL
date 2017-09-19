package com.tr.rp.varstore.types;

import com.tr.rp.varstore.arrays.Array;

public class ArrayType extends Type<Array> {

	ArrayType() {
		super("array");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof Array;
	}
}
