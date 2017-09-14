package com.tr.rp.exec;

import com.tr.rp.exceptions.RPLException;

public interface EvaluationErrorHandler {
	public void handleEvaluationError(RPLException e) throws RPLException; 
}
