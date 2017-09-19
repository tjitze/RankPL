package com.tr.rp.varstore.types;

import com.tr.rp.varstore.datastructures.PersistentSet;

public class SetType extends Type<PersistentSet<Object>> {

	SetType() {
		super("set");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentSet;
	}

}
