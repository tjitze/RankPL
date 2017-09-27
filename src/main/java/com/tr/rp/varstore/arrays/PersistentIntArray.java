package com.tr.rp.varstore.arrays;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * A persistent array of integers. The implementation is efficient 
 * (sqrt(n) time and space complexity for mutation operations). 
 */
public class PersistentIntArray implements PersistentArray {

	private final int[][] segments;
	private final int size;
	private final int square;

	// Hash code caching
	private int hashCode = 0;
	private boolean hashCodeComputed = false;
	
	public PersistentIntArray(Collection<Object> values) {
		this(values.size());
		int i = 0;
		for (Object v: values) {
			set(i++, v);
		}
	}
	
	public PersistentIntArray(Object ... values) {
		this(values.length);
		for (int i = 0; i < size; i++) {
			set(i, (int)values[i]);
		}
	}
	
	public PersistentIntArray(int size) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size));
		this.segments = new int[square][square];
	}
	
	private PersistentIntArray(int size, int[][] segments) {
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
		return getElement(index);
	}
	
	private final int getElement(int index) {
		int[] segment = segments[getSegmentIndex(index)];
		if (segment == null) {
			return 0;
		}
		return segment[getElementIndex(index)];
	}
	
	private synchronized void set(int index, Object value) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		int[] segment = segments[getSegmentIndex(index)];
		if (segment == null) {
			segment = new int[square];
			segments[getSegmentIndex(index)] = segment;
		}
		segment[getElementIndex(index)] = (int)value;
	}

	
	public synchronized PersistentArray getMutatedCopy(int index, Object value) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (!(value instanceof Integer)) {
			return downCast().getMutatedCopy(index, value);
		}
		int segmentIndex = getSegmentIndex(index);
		int elementIndex = getElementIndex(index);
		PersistentIntArray copy = new PersistentIntArray(size, Arrays.copyOf(segments, segments.length));
		copy.segments[segmentIndex] = Arrays.copyOf(segments[segmentIndex], segments[segmentIndex].length);
		copy.segments[segmentIndex][elementIndex] = (int)value;
		return copy;
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
		if (o instanceof PersistentIntArray) {
			PersistentIntArray other = (PersistentIntArray)o;
			if (size != other.size) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				if (getElement(i) != other.getElement(i)) {
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
