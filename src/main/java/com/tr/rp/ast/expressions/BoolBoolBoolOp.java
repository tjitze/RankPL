package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class BoolBoolBoolOp extends AbstractExpression {

	private final AbstractExpression e1, e2;
	private final BiFunction<Boolean, Boolean, Boolean> f;
	
	public BoolBoolBoolOp(BiFunction<Boolean, Boolean, Boolean> f, AbstractExpression e1, AbstractExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
		this.f = f;
	}

	@Override
	public void getVariables(Set<String> list) {
		e1.getVariables(list);
		e2.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return e1.hasRankExpression() || e2.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new BoolBoolBoolOp(f, (AbstractExpression)e1.transformRankExpressions(v, rank), (AbstractExpression)e2.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = e1.getEmbeddedFunctionCall();
		if (fc != null) return fc;
		return e2.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new BoolBoolBoolOp(f, (AbstractExpression)e1.replaceEmbeddedFunctionCall(fc, var), (AbstractExpression)e2.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		boolean b1 = e1.getValue(e, Type.BOOL);
		// Can we skip evaluating second operand?
		if (f.apply(b1, false) == f.apply(b1, true)) {
			return f.apply(b1, false);
		}
		boolean b2 = e2.getValue(e, Type.BOOL);
		return f.apply(b1, b2);
	}
	
	@Override
	public boolean hasDefiniteValue() {
		return e1.hasDefiniteValue() && e2.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return f.apply((boolean)e1.getDefiniteValue(Type.BOOL), (boolean)e2.getDefiniteValue(Type.BOOL));
	}
	
	public AbstractExpression getE1() {
		return e1;
	}

	public AbstractExpression getE2() {
		return e2;
	}
	
	public String toString() {
		return "(" + f.toString().replace("$1", StringTools.stripPars(e1.toString())).replace("$2", StringTools.stripPars(e2.toString())) + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof BoolBoolBoolOp) &&
				((BoolBoolBoolOp)o).e1.equals(e1) &&
				((BoolBoolBoolOp)o).e2.equals(e2) &&
				((BoolBoolBoolOp)o).f == f;
	}

	@Override
	public int hashCode() {
		return Objects.hash(e1, e2, f);
	}

}
