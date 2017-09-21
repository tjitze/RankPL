package com.tr.rp.varstore.arrays;

import java.util.List;

public class ArrayFactory {

	/**
	 * Construct a new persistent array containing given values. For efficiency, 
	 * different types of arrays may be used depending on the types of the values 
	 * and size of the array.
	 * 
	 * @param values Values to store in array.
	 * @return Array object containing values.
	 */
	public static PersistentArray newArray(Object[] values) {
		boolean allBool = true;
		boolean allInt = true;
		for (int i = 0; i < values.length; i++) {
			if (!(values[i] instanceof Boolean)) {
				allBool = false;
			}
			if (!(values[i] instanceof Integer)) {
				allInt = false;
			}
		}
		if (allBool) {
			if (values.length <= 27) {
				return new PersistentSmallBoolArray(values);
			} else {
				return new PersistentBoolArray(values);
			}
		} else if (allInt) {
			return new PersistentIntArray(values);
		} else {
			return new PersistentObjectArray(values);
		}
	}

	public static PersistentArray newArrayOf(Object ... values) {
		return newArray(values);
	}
	
	public static PersistentArray newArray(List<Object> values) {
		return newArray(values.toArray());
	}
}
