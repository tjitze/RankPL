package com.tr.rp.varstore.types;

public class IntType extends Type<Integer> {

	IntType() {
		super("integer");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof Integer;
	}

}
