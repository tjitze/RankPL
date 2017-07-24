package com.tr.rp.varstore.types;

import java.util.Collection;
import java.util.stream.Collectors;

import org.organicdesign.fp.collections.PersistentHashSet;
import org.organicdesign.fp.collections.PersistentVector;

/**
 * Persistent list data structure. Mutations create new set objects
 * without changing the original list.
 */
public class PersistentList<T> implements CollectionType<T> {

	private final PersistentVector<T> list;
	
	// Hash code caching
	private int hashCode = 0;
	private boolean hashCodeComputed = false;

	public PersistentList() {
		this.list = PersistentVector.empty();
	}
	
	private PersistentList(PersistentVector<T> list) {
		this.list = list;
	}

	public PersistentList<T> append(T o) {
		if (!list.isEmpty() && list.get(list.size() - 1).equals(o)) {
			return this;
		}
		return new PersistentList<T>(list.append(o));
	}
	
	public PersistentList<T> replace(int index, T element) {
		if (index >= list.size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return new PersistentList<T>(list.replace(index, element));
	}
	
	public T get(int index) {
		if (index >= list.size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return list.get(index);
	}
	
	public PersistentList<T> removeAll() {
		return new PersistentList<T>();
	}
	
	public int size() {
		return list.size();
	}
	
	public synchronized int hashCode() {
		if (!hashCodeComputed) {
			hashCode = list.hashCode();
			hashCodeComputed = true;
		}
		return hashCode;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PersistentList) {
			PersistentList<?> other = (PersistentList<?>)o;
			return hashCode() == other.hashCode() && list.equals(other.list);
		}
		return false;
	}
	
	public String toString() {
		return "List(" + list.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
	}

	@Override
	public Collection<T> asCollection() {
		return list;
	}

}
