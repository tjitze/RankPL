package com.tr.rp.varstore.arrays;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Persistent array of boolean values. Provides same efficient (sqrt(n) time
 * and space complexity) persistent copy mechanism as PersistentObjectArray,
 * but stores booleans as bits of integer elements.
 */
public final class PersistentBoolArray implements PersistentArray {

	private final int[][] segments;
	private final int size;
	private final int square;

	private int hashCode = 0;
	private boolean hashCodeComputed = false;
	
	public PersistentBoolArray(Collection<Boolean> values) {
		this(values.size());
		int i = 0;
		for (Object v: values) {
			set(i++, v);
		}
	}
	
	public PersistentBoolArray(Object ... values) {
		this(values.length);
		for (int i = 0; i < size; i++) {
			set(i, (boolean)values[i]);
		}
	}
	
	public PersistentBoolArray(int size) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size / 32));
		this.segments = new int[square][square];
	}
	
	private PersistentBoolArray(int size, int[][] segments) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size / 32));
		this.segments = segments;
	}

	/**
	 * Returns index of segment where given index resides
	 */
	private int getSegmentIndex(int index) {
		return (index / 32) / square;
	}
	
	/**
	 * Returns index of element (in segment) where given index resides
	 */
	private int getElementIndex(int index) {
		return (index / 32) % square;
	}

	/**
	 * Returns index of bit (in element) where given index resides
	 */
	private int getBitIndex(int index) {
		return (index % 32);
	}

	public int size() {
		return size;
	}
	
	public synchronized Object get(int index) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		int[] segment = segments[getSegmentIndex(index)];
		if (segment == null) {
			return false;
		}
		int word = segment[getElementIndex(index)];
		int bit = getBitIndex(index);
		return (word & (1 << bit)) != 0;
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
		if (value.equals(Boolean.FALSE)) {
			segment[getElementIndex(index)] &= ~(1 << getBitIndex(index));
		} else {
			segment[getElementIndex(index)] |= 1 << getBitIndex(index);
		}
	}

	
	public synchronized PersistentArray getMutatedCopy(int index, Object value) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (!(value instanceof Boolean)) {
			return downCast().getMutatedCopy(index, value);
		}
		int segmentIndex = getSegmentIndex(index);
		int elementIndex = getElementIndex(index);
		PersistentBoolArray copy = new PersistentBoolArray(size, Arrays.copyOf(segments, segments.length));
		copy.segments[segmentIndex] = Arrays.copyOf(segments[segmentIndex], segments[segmentIndex].length);
		if (value.equals(Boolean.FALSE)) {
			copy.segments[segmentIndex][elementIndex] &= ~(1 << getBitIndex(index));
		} else {
			copy.segments[segmentIndex][elementIndex] |= 1 << getBitIndex(index);
		}
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
		if (o instanceof PersistentBoolArray) {
			PersistentBoolArray other = (PersistentBoolArray)o;
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
