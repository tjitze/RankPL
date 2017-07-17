package com.tr.rp.varstore.types;

import org.organicdesign.fp.collections.PersistentHashMap;
import org.organicdesign.fp.collections.PersistentHashSet;

/**
 * Persistent map data structure. Mutations create new set objects
 * without changing the original map object.
 */
public class PersistentMap<T, U> {

	private final PersistentHashMap<T, U> map;
	
	// Hash code caching
	private int hashCode = 0;
	private boolean hashCodeComputed = false;

	public PersistentMap() {
		this.map = PersistentHashMap.empty();
	}
	
	private PersistentMap(PersistentHashMap<T, U> map) {
		this.map = map;
	}

	public U get(T key) {
		return map.get(key);
	}

	public boolean containsKey(T key) {
		return map.containsKey(key);
	}

	public PersistentMap<T, U> add(T key, U value) {
		if (map.get(key) == value) {
			return this;
		}
		return new PersistentMap<T,U>(map.assoc(key, value));
	}
	
	public PersistentMap<T, U> remove(T key) {
		if (!containsKey(key)) {
			return this;
		}
		return new PersistentMap<T, U>(map.without(key));
	}
	
	
	public PersistentMap<T, U> removeAll() {
		return new PersistentMap<T, U>();
	}
	
	public int size() {
		return map.size();
	}
	
	public synchronized int hashCode() {
		if (!hashCodeComputed) {
			hashCode = map.hashCode();
			hashCodeComputed = true;
		}
		return hashCode;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PersistentMap) {
			PersistentMap<?, ?> other = (PersistentMap<?, ?>)o;
			return hashCode() == other.hashCode() && map.equals(other.map);
		}
		return false;
	}

}
