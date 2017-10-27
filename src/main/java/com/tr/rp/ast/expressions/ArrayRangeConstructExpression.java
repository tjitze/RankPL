package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIllegalRangeException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.arrays.ArrayFactory;
import com.tr.rp.varstore.arrays.PersistentArray;
import com.tr.rp.varstore.types.Type;

/**
 * Constructs array consisting of integer values from beginExp (inclusive) to
 * endExp (exclusive)
 * 
 */
public class ArrayRangeConstructExpression extends AbstractExpression {

	private final AbstractExpression beginExp;
	private final AbstractExpression endExp;

	private PersistentArray cached = null;

	public ArrayRangeConstructExpression(AbstractExpression beginExp, AbstractExpression endExp) {
		this.beginExp = beginExp;
		this.endExp = endExp;
	}

	@Override
	public void getVariables(Set<String> list) {
		beginExp.getVariables(list);
		endExp.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return beginExp.hasRankExpression() || endExp.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression newBegin = beginExp.transformRankExpressions(v, rank);
		AbstractExpression newEnd = endExp.transformRankExpressions(v, rank);
		ArrayRangeConstructExpression a = new ArrayRangeConstructExpression(newBegin, newEnd);
		a.setLineNumber(getLineNumber());
		return a;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = beginExp.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		if (endExp != null) {
			return endExp.getEmbeddedFunctionCall();
		}
		return null;
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AbstractExpression newBegin = beginExp.replaceEmbeddedFunctionCall(fc, var);
		AbstractExpression newEnd = endExp.replaceEmbeddedFunctionCall(fc, var);
		ArrayRangeConstructExpression a = new ArrayRangeConstructExpression(newBegin, newEnd);
		a.setLineNumber(getLineNumber());
		return a;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		if (hasDefiniteValue())
			return getDefiniteValue();
		int begin = beginExp.getIntValue(e);
		int end = endExp.getIntValue(e);
		if (end < begin) {
			throw new RPLIllegalRangeException(begin, end, this);
		}
		return createArray(begin, end);
	}

	private PersistentArray createArray(int begin, int end) {
		Object[] range = new Object[end - begin];
		for (int i = 0; i < range.length; i++) {
			range[i] = begin + i;
		}
		return ArrayFactory.newArray(range);
	}

	@Override
	public boolean hasDefiniteValue() {
		return beginExp.hasDefiniteValue() && endExp.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		if (cached == null) {
			int begin = beginExp.getDefiniteValue(Type.INT);
			int end = endExp.getDefiniteValue(Type.INT);
			if (end < begin) {
				throw new RPLIllegalRangeException(begin, end, this);
			}
			cached = createArray(begin, end);
		}
		return cached;
	}

	public String toString() {
		return "[" + StringTools.stripPars(beginExp.toString()) 
			+ " ... " + StringTools.stripPars(endExp.toString())
				+ "]";
	}

	public boolean equals(Object o) {
		return (o instanceof ArrayRangeConstructExpression)
				&& ((ArrayRangeConstructExpression) o).beginExp.equals(beginExp)
				&& ((ArrayRangeConstructExpression) o).endExp.equals(endExp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), beginExp, endExp);
	}

}
