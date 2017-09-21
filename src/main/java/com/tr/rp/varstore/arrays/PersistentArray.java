package com.tr.rp.varstore.arrays;

/**
 * A persistent array interface. Implements mutation by copying.
 */
public interface PersistentArray {
	
	public int size();
	
	public Object get(int index);
	
	public PersistentArray getMutatedCopy(int index, Object value);
	
}
