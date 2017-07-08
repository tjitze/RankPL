package com.tr.rp.varstore.types;

public class BoolType extends Type<Boolean> {

	BoolType() {
		super("boolean");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof Boolean;
	}

}
