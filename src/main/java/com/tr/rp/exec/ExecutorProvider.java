package com.tr.rp.exec;

public interface ExecutorProvider {
	public Executor getExecutor(Executor out, ExecutionContext c);
}
