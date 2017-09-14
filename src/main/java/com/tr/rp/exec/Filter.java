package com.tr.rp.exec;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class Filter implements Executor {

	private final Executor out;
	private final Supplier<AbstractExpression> expSupplier;
	private int offset = -1;
	private Consumer<Integer> offsetListener;
	private EvaluationErrorHandler errorHandler;
	
	public Filter(Executor out, Supplier<AbstractExpression> expSupplier) {
		this.out = out;
		this.expSupplier = expSupplier;
	}
	
	public Filter(Executor out, AbstractExpression exp) {
		this.out = out;
		this.expSupplier = new Supplier<AbstractExpression>() {
			@Override
			public AbstractExpression get() {
				return exp;
			}
		};
	}
	
	@Override
	public void close() throws RPLException {
		out.close();
	}
	
	@Override
	public void push(State s) throws RPLException {
		if (getCheckedValue(s.getVarStore())) {
			if (offset == -1) {
				offset = s.getRank();
				if (offsetListener != null) {
					offsetListener.accept(offset);
				}
			}
			out.push(s.shiftDown(offset));
		}
 	}

	private boolean getCheckedValue(VarStore varStore) throws RPLException {
		try {
			return expSupplier.get().getValue(varStore, Type.BOOL);
		} catch (RPLException e) {
			if (errorHandler != null) {
				errorHandler.handleEvaluationError(e);
			}
			throw e;
		}
	}

	public void setErrorHandler(EvaluationErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}


	public void setOffsetListener(Consumer<Integer> offsetListener) {
		this.offsetListener = offsetListener;
	}

}
