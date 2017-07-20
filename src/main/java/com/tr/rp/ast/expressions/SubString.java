package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class SubString extends AbstractExpression {

	private final AbstractExpression input;
	private final AbstractExpression begin;
	private final AbstractExpression end;
	
	public SubString(AbstractExpression stringExp, AbstractExpression start, AbstractExpression end) {
		this.input = stringExp;
		this.begin = start;
		this.end = end;
	}

	@Override
	public void getVariables(Set<String> list) {
		input.getVariables(list);
		begin.getVariables(list);
		end.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new SubString(
				(AbstractExpression)input.replaceVariable(a, b),
				(AbstractExpression)begin.replaceVariable(a, b),
				(AbstractExpression)end.replaceVariable(a, b));
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		return input.needsRankExpressionTransformation() ||
					begin.needsRankExpressionTransformation() ||
					end.needsRankExpressionTransformation();
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		return new SubString(
				(AbstractExpression)input.doRankExpressionTransformation(v, rank),
				(AbstractExpression)begin.doRankExpressionTransformation(v, rank),
				(AbstractExpression)end.doRankExpressionTransformation(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = input.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		fc = begin.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		return end.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new SubString(
				(AbstractExpression)input.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)begin.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)end.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore vs) throws RPLException {
		String s = input.getValue(vs, Type.STRING);
		int beginIndex = begin.getValue(vs, Type.INT);
		if (beginIndex < 0 || beginIndex >= s.length()) {
			throw new RPLIndexOutOfBoundsException(beginIndex, s.length(), this);
		}
		int endIndex = end.getValue(vs, Type.INT);
		if (endIndex < beginIndex) {
			throw new RPLMiscException("Illegal substring range (" + beginIndex + ", " + endIndex + ")");
		}
		if (endIndex > s.length()) {
			throw new RPLIndexOutOfBoundsException(endIndex, s.length(), this);
		}
		return s.substring(beginIndex, endIndex);
	}

	@Override
	public boolean hasDefiniteValue() {
		return input.hasDefiniteValue() 
				&& begin.hasDefiniteValue()
				&& end.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		String s = input.getDefiniteValue(Type.STRING);
		int beginIndex = begin.getDefiniteValue(Type.INT);
		if (beginIndex < 0 || beginIndex >= s.length()) {
			throw new RPLIndexOutOfBoundsException(beginIndex, s.length(), this);
		}
		int endIndex = end.getDefiniteValue(Type.INT);
		if (endIndex < beginIndex) {
			throw new RPLMiscException("Illegal substring range (" + beginIndex + ", " + endIndex + ")");
		}
		if (endIndex > s.length()) {
			throw new RPLIndexOutOfBoundsException(endIndex, s.length(), this);
		}
		return s.substring(beginIndex, endIndex);
	}

	public String toString() {
		String es = input.toString();
		if (es.startsWith("(") && es.endsWith(")")) {
			es = es.substring(1, es.length()-1);
		}
		String beginIndex = begin.toString();
		if (beginIndex.startsWith("(") && beginIndex.endsWith(")")) {
			beginIndex = es.substring(1, beginIndex.length()-1);
		}
		String endIndex = end.toString();
		if (endIndex.startsWith("(") && endIndex.endsWith(")")) {
			endIndex = es.substring(1, endIndex.length()-1);
		}
		return "SubString(" + es + "," + beginIndex + ", " + endIndex + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof SubString) && 
				((SubString)o).input.equals(input) &&
				((SubString)o).begin.equals(begin) &&
				((SubString)o).end.equals(end);
	}

	@Override
	public int hashCode() {
		return Objects.hash(input, begin, end);
	}

}
