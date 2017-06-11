package com.tr.rp.ast.statements;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.Variable;

import static com.tr.rp.ast.expressions.Expressions.*;

public class Statements {

	public static Assign assign(String variable, int value) {
		return new Assign(new AssignmentTarget(variable), lit(value));
	}
	
	public static Assign assign(String variable, boolean value) {
		return new Assign(new AssignmentTarget(variable), lit(value));
	}
	
//	public static Assign assign(String variable, String value) {
//		return new Assign(new AssignmentTarget(variable), lit(value));
//	}
	
	public static Assign assign(String variable, AbstractExpression value) {
		return new Assign(new AssignmentTarget(variable), value);
	}

	public static Assign assign(AssignmentTarget target, int value) {
		return new Assign(target, lit(value));
	}
	
	public static Assign assign(AssignmentTarget target, boolean value) {
		return new Assign(target, lit(value));
	}
	
//	public static Assign assign(AssignmentTarget target, String value) {
//		return new Assign(target, lit(value));
//	}
	
	public static Assign assign(AssignmentTarget target, AbstractExpression value) {
		return new Assign(target, value);
	}

	public static Composition comp(AbstractStatement s1, AbstractStatement s2) {
		return new Composition(s1, s2);
	}
	
	public static Cut cut(int rank) {
		return new Cut(lit(rank));
	}

	public static Cut cut(AbstractExpression rank) {
		return new Cut(rank);
	}
	
	public static Dec dec(String variable) {
		return new Dec(new AssignmentTarget(variable));
	}

	public static Dec dec(AssignmentTarget target) {
		return new Dec(target);
	}
	
	public static ForStatement forStatement(AbstractStatement init, AbstractExpression forCondition, AbstractStatement next, AbstractStatement body) {
		return new ForStatement(init, forCondition, next, body);
	}
	
	public static IfElse ifElse(AbstractExpression condition, AbstractStatement thenStatement, AbstractStatement elseStatement) {
		return new IfElse(condition, thenStatement, elseStatement);
	}

	public static IfElse ifElse(AbstractExpression condition, AbstractStatement thenStatement) {
		return new IfElse(condition, thenStatement, new Skip());
	}

	public static Inc inc(String variable) {
		return new Inc(new AssignmentTarget(variable));
	}

	public static Inc inc(AssignmentTarget target) {
		return new Inc(target);
	}
	
	public static Observe observe(AbstractExpression condition) {
		return new Observe(condition);
	}

	public static ObserveJ observeJ(AbstractExpression condition, AbstractExpression rank) {
		return new ObserveJ(condition, rank);
	}

	public static ObserveJ observeJ(AbstractExpression condition, int rank) {
		return new ObserveJ(condition, lit(rank));
	}

	public static ObserveL observeL(AbstractExpression condition, AbstractExpression rank) {
		return new ObserveL(condition, rank);
	}

	public static ObserveL observeL(AbstractExpression condition, int rank) {
		return new ObserveL(condition, lit(rank));
	}

	public static PrintStatement print(AbstractExpression e) {
		return new PrintStatement(e);
	}
	
	public static RangeChoice rangeChoice(AssignmentTarget target, AbstractExpression beginExp, AbstractExpression endExp) {
		return new RangeChoice(target, beginExp, endExp);
	}

	public static RangeChoice rangeChoice(AssignmentTarget target, int begin, int end) {
		return new RangeChoice(target, lit(begin), lit(end));
	}

	public static RangeChoice rangeChoice(String variable, AbstractExpression beginExp, AbstractExpression endExp) {
		return new RangeChoice(new AssignmentTarget(variable), beginExp, endExp);
	}

	public static RangeChoice rangeChoice(String variable, int begin, int end) {
		return new RangeChoice(new AssignmentTarget(variable), lit(begin), lit(end));
	}
	
	public static RankedChoice rankedChoice(AbstractStatement s1, AbstractStatement s2, AbstractExpression rank) {
		return new RankedChoice(s1, s2, rank);
	}

	public static RankedChoice rankedChoice(AssignmentTarget target, AbstractExpression v1, AbstractExpression v2, 
			AbstractExpression rank) {
		return new RankedChoice(target, v1, v2, rank);
	}
	
	public static ReadFile readFile(AssignmentTarget target, AbstractExpression fileName) {
		return new ReadFile(target, fileName, ReadFile.InputMethod.NEWLINE_SEPARATED);
	}

	public static ReadFile readFile(String variable, AbstractExpression fileName) {
		return new ReadFile(new AssignmentTarget(variable), fileName, ReadFile.InputMethod.NEWLINE_SEPARATED);
	}
	
	public static Return returnStatement(AbstractExpression value) {
		return new Return(value);
	}
	
	public static Return returnStatement(String variable) {
		return new Return(new Variable(variable));
	}
	
	public static Skip skip() {
		return new Skip();
	}
	
	public static While whileStatement(AbstractExpression whileCondition, AbstractStatement body) {
		return new While(whileCondition, body);
	}
	
}
