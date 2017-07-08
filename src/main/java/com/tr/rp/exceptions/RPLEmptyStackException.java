package com.tr.rp.exceptions;

public class RPLEmptyStackException extends RPLException {
	
	/**
	 * @return Description of exception without reference to statement
	 */
	public String getDescription() {
		return "Attempted to pop value from empty stack";
	}
		
	public String toString() {
		return getDescription();
	}

}
