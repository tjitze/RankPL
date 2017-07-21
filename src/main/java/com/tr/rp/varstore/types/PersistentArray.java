package com.tr.rp.varstore.types;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A persistent list of objects. Mutation is done through the set method,
 * which creates a new PersistentList object and does not change the original 
 * list. The implementation is efficient (sqrt(n) time and space complexity
 * for mutation operations). 
 * 
 * TODO: consider replacing with PersistentVector
 */
public class PersistentArray {

	private final Object[][] segments;
	private final int size;
	private final int square;

	// Hash code caching
	private int hashCode = 0;
	private boolean hashCodeComputed = false;
	
	public PersistentArray(Collection<Object> values) {
		this(values.size());
		int i = 0;
		for (Object v: values) {
			set(i++, v);
		}
	}
	
	public PersistentArray(Object ... values) {
		this(values.length);
		for (int i = 0; i < size; i++) {
			set(i, values[i]);
		}
	}
	
	public PersistentArray(int size) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size));
		this.segments = new Object[square][square];
	}
	
	private PersistentArray(int size, Object[][] segments) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size));
		this.segments = segments;
	}

	public PersistentArray(int length, Supplier<Object> initValue) {
		this.size = length;
		this.square = (int)Math.ceil(Math.sqrt(length));
		this.segments = new Object[square][square];
		for (int i = 0; i < length; i++) {
			set(i, initValue.get());
		}
	}

	private int getSegment(int index) {
		return index / square;
	}
	
	private int getSegmentIndex(int index) {
		return index % square;
	}

	public int size() {
		return size;
	}
	
	public synchronized Object get(int index) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Object[] segment = segments[getSegment(index)];
		if (segment == null) {
			return null;
		}
		return segment[getSegmentIndex(index)];
	}
	
	private synchronized void set(int index, Object value) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Object[] segment = segments[getSegment(index)];
		if (segment == null) {
			segment = new Object[square];
			segments[getSegment(index)] = segment;
		}
		segment[getSegmentIndex(index)] = value;
	}

	
	public synchronized PersistentArray getMutatedCopy(int index, Object value) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		int segment = getSegment(index);
		int segmentIndex = getSegmentIndex(index);
		PersistentArray copy = new PersistentArray(size, segments);
		for (int i = 0; i < square; i++) {
			if (i == segment) {
				copy.segments[i] = Arrays.copyOf(segments[i], segments[i].length);
				copy.segments[i][segmentIndex] = value;
			} else {
				copy.segments[i] = segments[i];
			}
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
		if (o instanceof PersistentArray) {
			PersistentArray other = (PersistentArray)o;
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
