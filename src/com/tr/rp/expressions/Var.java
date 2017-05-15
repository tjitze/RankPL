//package com.tr.rp.expressions;
//
//import java.util.Objects;
//import java.util.Set;
//
//import com.tr.rp.core.Expression;
//import com.tr.rp.core.LanguageElement;
//import com.tr.rp.core.VarStore;
//import com.tr.rp.exceptions.RPLException;
//
//public class Var extends Expression {
//
//	private final String name;
//	
//	public Var(String name) {
//		this.name = name;
//	}
//	
//	@Override
//	public boolean containsVariable(String var) {
//		return var.equals(name);
//	}
//
//	@Override
//	public void getVariables(Set<String> list) {
//		list.add(name);
//	}
//
//	@Override
//	public LanguageElement replaceVariable(String a, String b) {
//		if (a.equals(name)) {
//			return new Var(b);
//		} else {
//			return this;
//		}
//	}
//
//	@Override
//	public boolean hasRankExpression() {
//		return false;
//	}
//
//	@Override
//	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
//		return this;
//	}
//
//	@Override
//	public FunctionCall getEmbeddedFunctionCall() {
//		return null;
//	}
//
//	@Override
//	public Expression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
//		return this;
//	}
//
//	@Override
//	public Object getValue(VarStore e) throws RPLException {
//		return e.getValue(name);
//	}
//
//	@Override
//	public boolean hasDefiniteValue() {
//		return false;
//	}
//
//	@Override
//	public Object getDefiniteValue() throws RPLException {
//		return null;
//	}
//
//	public String toString() {
//		return name;
//	}
//	
//	public boolean equals(Object o) {
//		return ((o instanceof Var) && Objects.equals(((Var)o).name, name));
//	}
//}
