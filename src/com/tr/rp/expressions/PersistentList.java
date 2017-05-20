package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A persistent list of objects. Mutation is done through the set method,
 * which creates a new PersistentList object and does not change the original 
 * list. The implementation is efficient (sqrt(n) time and space complexity
 * for mutation operations). 
 */
public class PersistentList {

	private final Object[][] segments;
	private final int size;
	private final int square;
	
	public PersistentList(Collection<Object> values) {
		this(values.size());
		int i = 0;
		for (Object v: values) {
			set(i++, v);
		}
	}
	
	public PersistentList(Object ... values) {
		this(values.length);
		for (int i = 0; i < size; i++) {
			set(i, values[i]);
		}
	}
	
	public PersistentList(int size) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size));
		this.segments = new Object[square][square];
	}
	
	private PersistentList(int size, Object[][] segments) {
		this.size = size;
		this.square = (int)Math.ceil(Math.sqrt(size));
		this.segments = segments;
	}

	public PersistentList(int length, Supplier<Object> initValue) {
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
	
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
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

	
	public synchronized PersistentList getMutatedCopy(int index, Object value) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}
		int segment = getSegment(index);
		int segmentIndex = getSegmentIndex(index);
		PersistentList copy = new PersistentList(size, segments);
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
		if (o instanceof PersistentList) {
			PersistentList other = (PersistentList)o;
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
}
