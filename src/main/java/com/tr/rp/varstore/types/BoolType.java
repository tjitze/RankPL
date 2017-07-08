package com.tr.rp.varstore.types;

public class BoolType extends Type<Boolean> {

	public BoolType() {
		super("boolean");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof Boolean;
	}

}
