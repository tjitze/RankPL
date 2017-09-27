package com.tr.rp.executors;

import com.tr.rp.exceptions.RPLException;

public interface EvaluationErrorHandler {
	public void handleEvaluationError(RPLException e) throws RPLException; 
}
