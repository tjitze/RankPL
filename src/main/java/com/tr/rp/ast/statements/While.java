package com.tr.rp.ast.statements;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;
import com.tr.rp.exec.RankTransformer;
import com.tr.rp.exec.State;
import com.tr.rp.varstore.types.Type;

public class While extends AbstractStatement {
	
	/** Optional statement to execute before evaluating while condition */
	private AbstractStatement preStatement;

	/** While condition */
	private AbstractExpression whileCondition;
	
	/** While statement body */
	private AbstractStatement body;
	
	/** Statement that is attached to exception thrown when evaluating whileCondition */
	private AbstractStatement exceptionSource;
		
	public While(AbstractExpression whileCondition, AbstractStatement body) {
		this.whileCondition = whileCondition;
		this.body = body;
		preStatement = null;
		exceptionSource = this;
	}
	
	private While(AbstractExpression whileCondition, AbstractStatement body, AbstractStatement preStatement) {
		this.whileCondition = whileCondition;
		this.body = body;
		this.preStatement = preStatement;
		exceptionSource = this;
	}

	public AbstractExpression getWhileCondition() {
		return whileCondition;
	}
		
	public AbstractStatement getPreStatement() {
		return preStatement;
	}
		
	public AbstractStatement getBody() {
		return body;
	}
	public Executor getIteration(Supplier<AbstractExpression> exp, Executor out, int shift, ExecutionContext c, int depth, int unrollDepth, LinkedList<Callable> queue) {
		Executor iterate = new Executor() {

			private Executor next;
			private int offset = -1;
			
			@Override
			public void close() throws RPLException {
				if (next != null) {
					if (depth % unrollDepth == 0) { 
						queue.add(new Callable() {
							@Override
							public void call() throws RPLException {
								next.close();
							}
						});
					} else {
						next.close();
					}
				} else {
					out.close();
				}
			}

			@Override
			public void push(State s) throws RPLException {
				if (!getCheckedValue(exp.get(), s)) {
					out.push(s.shiftUp(shift));
				} else {
					if (next == null) {
						offset = s.getRank();
						next = body.getExecutor(getIteration(exp, out, shift + offset, c, depth + 1, unrollDepth, queue), c);
					}
					if (depth % unrollDepth == 0) {
						queue.add(new Callable() {
							@Override
							public void call() throws RPLException {
								next.push(s.shiftDown(offset));							
							}
						});
					} else {
						next.push(s.shiftDown(offset));
					}
				}
			}
		};
		return iterate;
	}

	private boolean getCheckedValue(AbstractExpression exp, State s) throws RPLException {
		try {
			return exp.getValue(s.getVarStore(), Type.BOOL);
		} catch (RPLException e) {
			e.setStatement(exceptionSource);
			throw e;
		}
	}

	public void setExceptionSource(AbstractStatement exceptionSource) {
		this.exceptionSource = exceptionSource;
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		return WhileWorker.createExecutor(out, c, this);
	}	
	
	public Executor getWhileExecutor(Executor out, ExecutionContext c, int unrollDepth) {
		LinkedList<Callable> queue = new LinkedList<Callable>();
		RankTransformer<AbstractExpression> transformWhileCond = RankTransformer.create(whileCondition);
		Executor iteration = getIteration(transformWhileCond, out, 0, c, 0, unrollDepth, queue);
		Executor trampoline = new Executor() {
			@Override
			public void close() throws RPLException {
				iteration.close();
				while (!queue.isEmpty()) queue.remove().call();
			}

			@Override
			public void push(State s) throws RPLException {
				iteration.push(s);
				while (!queue.isEmpty()) queue.remove().call();
			}
		};
		transformWhileCond.setOutput(trampoline, exceptionSource);
		return transformWhileCond;
	}
		
	public boolean equals(Object o) {
		return o instanceof While &&
				((While)o).whileCondition.equals(whileCondition) &&
				((While)o).body.equals(body);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new While((AbstractExpression)whileCondition.replaceVariable(a, b),
				(AbstractStatement)this.body.replaceVariable(a, b));
	}
	
	public String toString() {
		String expString = whileCondition.toString();
		if (preStatement != null && preStatement instanceof FunctionCallForm) {
			
		}
		if (!(expString.startsWith("(") && expString.endsWith(")"))) {
			expString = "(" + expString + ")";
		}
		String ss = body.toString();
		if (preStatement != null && preStatement instanceof FunctionCallForm) {
			expString = ((FunctionCallForm)preStatement).transformStatement(expString);
			ss = ((FunctionCallForm)preStatement).transformStatement(ss);
		}
		return "while " + expString + " do " + ss;
	}
	
	@Override
	public void getVariables(Set<String> list) {
		body.getVariables(list);
		whileCondition.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		if (preStatement != null) {
			throw new UnsupportedOperationException();
		}
		AbstractStatement sr = body.rewriteEmbeddedFunctionCalls();
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(whileCondition);
		if (rewrittenExp.isRewritten()) {
			return new While(rewrittenExp.getExpression(), sr, new FunctionCallForm(new Skip(), rewrittenExp.getAssignments()));
		} else {
			return new While(whileCondition, sr);
		}
	}	

	@Override
	public void getAssignedVariables(Set<String> variables) {
		body.getAssignedVariables(variables);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), preStatement, whileCondition, body);
	}
		
	private static abstract class Callable {
		public abstract void call() throws RPLException;
	}
}
