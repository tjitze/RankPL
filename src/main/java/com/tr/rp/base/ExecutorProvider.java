package com.tr.rp.base;

import com.tr.rp.executors.Executor;

public interface ExecutorProvider {

	/**
	 * Return executor for this statement
	 */
	public abstract Executor getExecutor(Executor out, ExecutionContext c);

}
