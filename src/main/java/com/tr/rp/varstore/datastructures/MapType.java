package com.tr.rp.varstore.datastructures;

import com.tr.rp.varstore.types.Type;

public class MapType extends Type<PersistentMap<Object, Object>> {

	public MapType() {
		super("map");
	}
	
	@Override
	public boolean test(Object o) {
		return o instanceof PersistentMap;
	}

}
