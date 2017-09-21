package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeMismatchException;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.varstore.VarStore;

/**
 * The equality (==) expression. This expression evaluates to true iff 
 * both arguments have the same type represent the same value (whether
 * integer, boolean, string, or list).
 */
public class Equals extends AbstractExpression {

	private final AbstractExpression e1, e2;
	
	public Equals(AbstractExpression e1, AbstractExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	@Override
	public void getVariables(Set<String> list) {
		e1.getVariables(list);
		e2.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Equals((AbstractExpression)e1.replaceVariable(a, b), (AbstractExpression)e2.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return e1.hasRankExpression() || e2.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new Equals((AbstractExpression)e1.transformRankExpressions(v, rank), (AbstractExpression)e2.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = e1.getEmbeddedFunctionCall();
		if (fc != null) return fc;
		return e2.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new Equals((AbstractExpression)e1.replaceEmbeddedFunctionCall(fc, var), (AbstractExpression)e2.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore v) throws RPLException {
		Object v1 = e1.getValue(v);
		Object v2 = e2.getValue(v);
		if (v1 == null) {
			throw new RPLUndefinedException(e1);
		}
		if (v2 == null) {
			throw new RPLUndefinedException(e2);
		}
//		if (!Objects.equals(v1.getClass(), v2.getClass())) {
//			throw new RPLTypeMismatchException(v1, v2, this);
//		}
		return Objects.equals(v1, v2);
	}
	
	@Override
	public boolean hasDefiniteValue() {
		return e1.hasDefiniteValue() && e2.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		Object v1 = e1.getDefiniteValue();
		Object v2 = e2.getDefiniteValue();
		if (v1 == null) {
			throw new RPLUndefinedException(e1);
		}
		if (v2 == null) {
			throw new RPLUndefinedException(e2);
		}
		if (!Objects.equals(v1.getClass(), v2.getClass())) {
			throw new RPLTypeMismatchException(v1, v2, this);
		}
		return Objects.equals(v1, v2);
	}
	
	public AbstractExpression getE1() {
		return e1;
	}

	public AbstractExpression getE2() {
		return e2;
	}
	
	public String toString() {
		return "(" + e1 + " == " + e2 + ")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof Equals) &&
				((Equals)o).e1.equals(e1) &&
				((Equals)o).e2.equals(e2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(e1, e2);
	}

}
