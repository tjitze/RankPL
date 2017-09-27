package com.tr.rp.ast.statements;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;

/**
 * Push item using push method. Use closeState to indicate closure
 * will trigger computation, get output using pull()
 * if pull() returns resumeState, no more items can be generated until next item is pushed.
 * if pull() returns closeState, output is closed.
 *
 */
public class WhileWorker implements Runnable {

	private final While ws;

	private final ExecutionContext c;
	
	private final SynchronousQueue<State> in = new SynchronousQueue<State>();
	private final SynchronousQueue<State> out = new SynchronousQueue<State>();
	private final SynchronousQueue<Object> wait = new SynchronousQueue<Object>();
	
	private RPLException exception = null;

	public static final State CLOSE = new State(null, 0);
	public static final State RESUME = new State(null, 0);
	public static final State EXCEPTION = new State(null, 0);

	private static final AtomicInteger threadCount = new AtomicInteger(0);
	
	private static final int MAX_WORKER_THREAD_COUNT = 2000;

	private static final int UNROLL_DEPTH = 100;
	
	public WhileWorker(While ws, ExecutionContext c) {
		this.ws = ws;
		this.c = c;
	}
	
	public final synchronized void push(State s) {
		try {
			in.put(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public final State pull() {
		try {
			return out.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public final RPLException getException() {
		return exception;
	}
	
	public final void resume() {
		try {
			wait.put(new Object());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		threadCount.incrementAndGet();
		Executor exec = ws.getWhileExecutor(new Executor() {
			@Override
			public void close() throws RPLException {
				try {
					out.put(CLOSE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
			@Override
			public void push(State s) throws RPLException {
				try {
					out.put(s);
					wait.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, c, UNROLL_DEPTH);
		try {
			loop: while (true) {
				State s = in.take();
				try {
					if (s == CLOSE) {
						exec.close();
						break loop;
					} else {
						exec.push(s);
					}
				} catch (RPLException ex) {
					exception = ex;
					out.put(EXCEPTION);
					break;
				}
				out.put(RESUME);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		threadCount.decrementAndGet();
	}
	
	public static final Executor createExecutor(Executor out, ExecutionContext c, While ws) {
		if (threadCount.get() > MAX_WORKER_THREAD_COUNT) {
			return ws.getWhileExecutor(out, c, 1);
		}
		WhileWorker worker = new WhileWorker(ws, c);
		Thread thread = new Thread(worker);
		thread.start();
		return new Executor() {

			@Override
			public void close() throws RPLException {
				worker.push(WhileWorker.CLOSE);
				State s = worker.pull();
				while (s != WhileWorker.CLOSE && s != WhileWorker.RESUME) {
					out.push(s);
					worker.resume();
					s = worker.pull();
				}
				if (s == WhileWorker.CLOSE) {
					out.close();
				}
			}

			@Override
			public void push(State s) throws RPLException {
				worker.push(s);
				s = worker.pull();
				while (s != WhileWorker.CLOSE && s != WhileWorker.RESUME && s != WhileWorker.EXCEPTION) {
					out.push(s);
					worker.resume();
					s = worker.pull();
				}
				if (s == WhileWorker.EXCEPTION) {
					throw worker.getException();
				}
				if (s == WhileWorker.CLOSE) {
					out.close();
				}
			}
			
		};
	}	

}
