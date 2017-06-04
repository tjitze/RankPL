package com.tr.rp.exceptions;

public class RPLInterruptedException extends RPLException {

	@Override
	public String getDescription() {
		return "Execution interrupted.";
	}

}
