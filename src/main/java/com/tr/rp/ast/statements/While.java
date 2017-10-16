package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.BranchingExecutor;
import com.tr.rp.executors.Deduplicator;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.Guard;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.varstore.types.Type;

public class While extends AbstractStatement {
	
	/** Optional statement to execute before evaluating while condition */
	private AbstractStatement preStatement;

	/** While condition */
	private AbstractExpression whileCondition;
	
	/** While statement body */
	private AbstractStatement body;
	
	private static final int ITERATION_UNROLL_DEPTH = 25;
	
	public While(AbstractExpression whileCondition, AbstractStatement body) {
		this.whileCondition = whileCondition;
		this.body = body;
		preStatement = null;
	}
	
	private While(AbstractExpression whileCondition, AbstractStatement body, AbstractStatement preStatement) {
		this.whileCondition = whileCondition;
		this.body = body;
		this.preStatement = preStatement;
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
	public Executor getIteration(Supplier<AbstractExpression> exp, Executor out, int shift, ExecutionContext c, int depth, LinkedList<Callable> def) {
		Executor out2 = new Executor() {

			private Executor next = null;
			private int normalize = -1;
			
			@Override
			public void close() throws RPLException {
				if (depth >= ITERATION_UNROLL_DEPTH) {
					def.add(new Callable() {
						@Override
						public void call() throws RPLException {
							if (next == null) {
								out.close();
							} else {
								next.close();
							}
						}
					});
				} else {
					if (next == null) {
						out.close();
					} else {
						next.close();
					}
				}
			}

			@Override
			public void push(State s) throws RPLException {
				if (next == null && !getCheckedExpValue(exp.get(), s)) {
					out.push(s.shiftUp(shift));
				} else {
					if (next == null) {
						normalize = s.getRank();
						next = getIteration(exp, out, shift + normalize, c, depth + 1, def);
					}
					if (depth >= ITERATION_UNROLL_DEPTH) {
						def.add(new Callable() {
							@Override
							public void call() throws RPLException {
								next.push(s.shiftDown(normalize));
							}
						});
					} else {
						next.push(s.shiftDown(normalize));
					}
				}
			}
		};
		return new BranchingExecutor(exp, body, new Skip(), out2, c) {
			public void handleConditionException(RPLException e) throws RPLException {
				throw e;
			}
		};
	}

	private boolean getCheckedExpValue(AbstractExpression exp, State s) throws RPLException {
		try {
			return exp.getValue(s.getVarStore(), Type.BOOL);
		} catch (RPLException e) {
			handleConditionException(e);
			return false;
		}
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		LinkedList<Callable> def = new LinkedList<Callable>();
		Executor e = getIteration(() -> whileCondition, new Deduplicator(Guard.checkIfEnabled(out)), 0, c, 0, def);
		return new Executor() {
			@Override
			public void close() throws RPLException {
				while (!def.isEmpty()) def.removeFirst().call();
				e.close();
			}

			@Override
			public void push(State s) throws RPLException {
				while (!def.isEmpty()) def.removeFirst().call();
				e.push(s);
			}
		};
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
	
	/**
	 * Override to handle exceptions resulting from condition evaluation
	 */
	public void handleConditionException(RPLException e) throws RPLException {
		e.setStatement(this);
		throw e;
	}

}
