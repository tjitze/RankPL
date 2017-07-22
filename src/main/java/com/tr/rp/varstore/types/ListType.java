package com.tr.rp.varstore.types;

public class ListType extends Type<PersistentList<Object>> {

	ListType() {
		super("list");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentList;
	}

}
