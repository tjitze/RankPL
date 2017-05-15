package com.tr.rp.exceptions;

public class RPLIndexOutOfBoundsException extends RPLException {

	private final int index;
	private final int size;
	private boolean hasIndexAndSize;
	
	public RPLIndexOutOfBoundsException(int index, int size) {
		this.index = index;
		this.size = size;
		this.hasIndexAndSize = true;
	}
	
	public RPLIndexOutOfBoundsException() {
		this.index = -1;
		this.size = -1;
		this.hasIndexAndSize = false;
	}
	
	public String getDescription() {
		return "Array or string index out of bounds" +
				(hasIndexAndSize? (" (referencing index " + index + " of array or string of length " + size + ")"): "");
	}
}
