package com.tr.rp.exceptions;

public class RPLMiscException extends RPLException {

	private String description;
	
	public RPLMiscException(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
