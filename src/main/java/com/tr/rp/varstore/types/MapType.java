package com.tr.rp.varstore.types;

public class MapType extends Type<PersistentMap<Object, Object>> {

	MapType() {
		super("map");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentMap;
	}

}
