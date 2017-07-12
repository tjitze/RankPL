package com.tr.rp.varstore.types;

import java.util.Objects;
import java.util.function.Consumer;

import com.tr.rp.exceptions.RPLEmptyStackException;

/**
 * Persistent stack data structure. Mutations create new stack instances
 * without changing the original stack object.
 */
public class PersistentStack<T> {

	private final PersistentStack<T> parent;
	private final T element;
	private final int size;
	private final int hashCode;
	
	/**
	 * Construct a new empty stack
	 */
	public PersistentStack() {
		parent = null;
		element = null;
		size = 0;
		hashCode = 0;
	}

	/**
	 * Construct stack based on 'bottom' stack plus top element.
	 * 
	 * @param element Top element
	 * @param parent Bottom stack
	 */
	private PersistentStack(T element, PersistentStack<T> parent) {
		Objects.requireNonNull(element);
		Objects.requireNonNull(parent);
		this.parent = parent;
		this.element = element;
		this.size = parent.size + 1;
		this.hashCode = Objects.hash(parent, element);
	}

	/**
	 * @param o Element to push
	 * @return New stack, same as current one, with o pushed
	 */
	public PersistentStack<T> push(T o) {
		return new PersistentStack<T>(o, this);
	}
	
	/**
	 * Pop value from stack. Throws exception if stack is empty.
	 * 
	 * @param value Consumer for popped value
	 * @return New stack, same as current one, with top value popped
	 */
	public PersistentStack<T> pop(Consumer<T> value) throws RPLEmptyStackException {
		if (element == null) {
			throw new RPLEmptyStackException();
		}
		value.accept(element);
		return parent;
	}

	/**
	 * @return Top value of stack
	 */
	public T peek() {
		return element;
	}
	
	/**
	 * @return Size of stack
	 */
	public int size() {
		return size;
	}
	
	public int hashCode() {
		return hashCode;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PersistentStack) {
			PersistentStack<?> other = (PersistentStack<?>)o;
			return hashCode == other.hashCode && internalEquals(other);
		}
		return false;
	}
	
	private boolean internalEquals(PersistentStack<?> other) {
		return other.element.equals(element) && other.internalEquals(this);
	}

}
