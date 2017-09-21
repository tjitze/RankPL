package com.tr.rp.varstore.arrays;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A persistent array of objects. The implementation is efficient 
 * (sqrt(n) time and space complexity for mutation operations). 
 * 
 * TODO: consider replacing with PersistentVector
 */
public class PersistentObjectArray implements PersistentArray {

	private final Object[][] segments;
	private final int size;
	private final int square;

	// Hash code caching
	private int hashCode = 0;
	private boolean hashCodeComputed = false;
	
	public PersistentObjectArray(Collection<Object> values) {
		this(values.size());
		int i = 0;
		for (Object v: values) {
			set(i++, v);
		}
	}
	
	public PersistentObjectArray(Object ... values) {
		this(values.length);
		for (int i = 0; i < size; i++) {
			set(i, values[i]);
		}
	}
	
	public PersistentObjectArray(int size) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size));
		this.segments = new Object[square][square];
	}
	
	private PersistentObjectArray(int size, Object[][] segments) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size));
		this.segments = segments;
	}
	
	/**
	 * Returns index of segment where given index resides
	 */
	private int getSegmentIndex(int index) {
		return index / square;
	}
	
	/**
	 * Returns index of element (in segment) where given index resides
	 */
	private int getElementIndex(int index) {
		return index % square;
	}

	public int size() {
		return size;
	}
	
	public synchronized Object get(int index) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Object[] segment = segments[getSegmentIndex(index)];
		if (segment == null) {
			return null;
		}
		return segment[getElementIndex(index)];
	}
	
	protected synchronized void set(int index, Object value) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Object[] segment = segments[getSegmentIndex(index)];
		if (segment == null) {
			segment = new Object[square];
			segments[getSegmentIndex(index)] = segment;
		}
		segment[getElementIndex(index)] = value;
	}

	
	public synchronized PersistentObjectArray getMutatedCopy(int index, Object value) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		int segmentIndex = getSegmentIndex(index);
		int elementIndex = getElementIndex(index);
		PersistentObjectArray copy = new PersistentObjectArray(size, Arrays.copyOf(segments, segments.length));
		copy.segments[segmentIndex] = Arrays.copyOf(segments[segmentIndex], segments[segmentIndex].length);
		copy.segments[segmentIndex][elementIndex] = value;
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
		if (o instanceof PersistentObjectArray) {
			PersistentObjectArray other = (PersistentObjectArray)o;
			if (size != other.size) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				if (!Objects.equals(get(i), other.get(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public synchronized int hashCode() {
		if (!hashCodeComputed) {
			hashCode = Objects.hash(size, Arrays.deepHashCode(segments));
			hashCodeComputed = true;
		}
		return hashCode;
	}
}
