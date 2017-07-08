package com.tr.rp.varstore.types;

public class IntType extends Type<Integer> {

	public IntType() {
		super("integer");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof Integer;
	}

}
