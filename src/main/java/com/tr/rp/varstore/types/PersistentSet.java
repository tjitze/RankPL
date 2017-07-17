package com.tr.rp.varstore.types;

import org.organicdesign.fp.collections.PersistentHashSet;

/**
 * Persistent set data structure. Mutations create new set objects
 * without changing the original set object.
 */
public class PersistentSet<T> {

	private final PersistentHashSet<T> set;
	
	// Hash code caching
	private int hashCode = 0;
	private boolean hashCodeComputed = false;

	public PersistentSet() {
		this.set = PersistentHashSet.empty();
	}
	
	private PersistentSet(PersistentHashSet<T> set) {
		this.set = set;
	}

	public boolean contains(T o) {
		return set.contains(o);
	}

	public PersistentSet<T> add(T o) {
		if (set.contains(o)) {
			return this;
		}
		return new PersistentSet<T>(set.put(o));
	}
	
	public PersistentSet<T> remove(T o) {
		if (!set.contains(o)) {
			return this;
		}
		return new PersistentSet<T>(set.without(o));
	}
	
	
	public PersistentSet<T> removeAll() {
		return new PersistentSet<T>();
	}
	
	public int size() {
		return set.size();
	}
	
	public synchronized int hashCode() {
		if (!hashCodeComputed) {
			hashCode = set.hashCode();
			hashCodeComputed = true;
		}
		return hashCode;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PersistentSet) {
			PersistentSet<?> other = (PersistentSet<?>)o;
			return hashCode() == other.hashCode() && set.equals(other.set);
		}
		return false;
	}

}
