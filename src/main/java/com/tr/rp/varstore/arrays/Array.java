package com.tr.rp.varstore.arrays;

public interface Array {
	
	public int size();
	
	public Object get(int index);
	
	public Array getMutatedCopy(int index, Object value);
	
}
