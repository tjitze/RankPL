package com.tr.rp.varstore.types;

public class StringType extends Type<String> {

	public StringType() {
		super("string");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof String;
	}

}
