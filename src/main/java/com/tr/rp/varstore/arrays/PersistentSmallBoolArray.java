package com.tr.rp.varstore.arrays;

/**
 * A boolean array holding at most 27 elements and a size, 
 * stored using just one integer.
 */
public final class PersistentSmallBoolArray implements PersistentArray {
	
	private final int value;
	
	private static final int MAX_SIZE = 27;
	private static final byte SIZE_MASK = 0x1F;
	private static final int V_SHIFT = 5;

	private PersistentSmallBoolArray(int value) {
		this.value = value;
	}
	
	public PersistentSmallBoolArray(Object[] values) {
		if (values.length > MAX_SIZE) {
			throw new IllegalArgumentException();
		}
		int size = values.length & SIZE_MASK;
		int initValue = size;
		for (int index = 0; index < size; index++) {
			if ((Boolean)values[index]) {
				initValue |= 1 << (index + V_SHIFT);
			} else {
				initValue &= ~(1 << (index + V_SHIFT));
			}
		}
		this.value = initValue;
	}

	@Override
	public int size() {
		return value & SIZE_MASK;
	}

	@Override
	public Object get(int index) {
		return getElement(index);
	}

	public final boolean getElement(int index) {
		if (index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return ((value >> (index + V_SHIFT)) & 1) != 0;
	}
	

	@Override
	public PersistentArray getMutatedCopy(int index, Object flag) {
		if (!(flag instanceof Boolean)) {
			return downCast().getMutatedCopy(index, flag);
		}
		if (index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (flag.equals(Boolean.FALSE)) {
			return new PersistentSmallBoolArray(value & ~(1 << (index + V_SHIFT)));
			
		} else {
			return new PersistentSmallBoolArray(value | 1 << (index + V_SHIFT));
		}
	}
	
	private PersistentArray downCast() {
		PersistentObjectArray copy = new PersistentObjectArray(size());
		for (int i = 0; i < size(); i++) {
			copy.set(i, get(i));
		}
		return copy;
	}

	public String toString() {
		String res = "[";
		for (int i = 0; i < size(); i++) {
			res += get(i);
			if (i != size() - 1) res += ", ";
		}
		return res + "]";
	}
	
	public boolean equals(Object o) {
		if (o instanceof PersistentSmallBoolArray) {
			return ((PersistentSmallBoolArray)o).value == value;
		}
		return false;
	}
	
	public int hashCode() {
		return value;
	}

}
