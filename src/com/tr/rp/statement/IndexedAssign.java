package com.tr.rp.statement;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;

/**
 * Array assignment. Sets value of element of an array, where
 * the index is determined by a numeric expression.
 */
public class IndexedAssign extends Assign {

	private NumExpression index;
	
	public IndexedAssign(String var, NumExpression index, NumExpression exp) {
		super(var, exp);
		this.index = index;
	}
	
	public IndexedAssign(String var, NumExpression index, int value) {
		super(var, value);
		this.index = index;
	}

	public IndexedAssign(String a, NumExpression index, String b) {
		super(a, b);
		this.index = index;
	}

	public IndexedAssign(String a, String indexVar, NumExpression exp) {
		super(a, exp);
		this.index = new Var(indexVar);
	}
	
	public IndexedAssign(String a, String indexVar, int value) {
		super(a, value);
		this.index = new Var(indexVar);
	}

	public IndexedAssign(String a, int index, int value) {
		super(a, value);
		this.index = new IntLiteral(index);
	}


	public IndexedAssign(String a, String indexVar, String b) {
		super(a, b);
		this.index = new Var(indexVar);
	}

	public String getVar(VarStore v) {
		return VarStore.getIndexedVar(super.getVar(v), index.getVal(v));
	}
}
