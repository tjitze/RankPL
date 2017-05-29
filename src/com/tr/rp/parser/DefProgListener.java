// Generated from /Users/tjitze/Desktop/workspace/RankPL/src/com/tr/rp/parser/DefProg.g by ANTLR 4.5

package com.tr.rp.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DefProgParser}.
 */
public interface DefProgListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DefProgParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(DefProgParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(DefProgParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(DefProgParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(DefProgParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#functiondef_or_statement}.
	 * @param ctx the parse tree
	 */
	void enterFunctiondef_or_statement(DefProgParser.Functiondef_or_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#functiondef_or_statement}.
	 * @param ctx the parse tree
	 */
	void exitFunctiondef_or_statement(DefProgParser.Functiondef_or_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#functiondef}.
	 * @param ctx the parse tree
	 */
	void enterFunctiondef(DefProgParser.FunctiondefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#functiondef}.
	 * @param ctx the parse tree
	 */
	void exitFunctiondef(DefProgParser.FunctiondefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(DefProgParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(DefProgParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ChoiceAssignmentStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterChoiceAssignmentStatement(DefProgParser.ChoiceAssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ChoiceAssignmentStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitChoiceAssignmentStatement(DefProgParser.ChoiceAssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RangeChoiceStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRangeChoiceStatement(DefProgParser.RangeChoiceStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RangeChoiceStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRangeChoiceStatement(DefProgParser.RangeChoiceStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(DefProgParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(DefProgParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(DefProgParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(DefProgParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(DefProgParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(DefProgParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObserveStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterObserveStatement(DefProgParser.ObserveStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObserveStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitObserveStatement(DefProgParser.ObserveStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObserveLStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterObserveLStatement(DefProgParser.ObserveLStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObserveLStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitObserveLStatement(DefProgParser.ObserveLStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObserveJStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterObserveJStatement(DefProgParser.ObserveJStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObserveJStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitObserveJStatement(DefProgParser.ObserveJStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SkipStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSkipStatement(DefProgParser.SkipStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SkipStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSkipStatement(DefProgParser.SkipStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RankedChoiceStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRankedChoiceStatement(DefProgParser.RankedChoiceStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RankedChoiceStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRankedChoiceStatement(DefProgParser.RankedChoiceStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndifferentChoiceStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIndifferentChoiceStatement(DefProgParser.IndifferentChoiceStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndifferentChoiceStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIndifferentChoiceStatement(DefProgParser.IndifferentChoiceStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementSequence}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementSequence(DefProgParser.StatementSequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementSequence}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementSequence(DefProgParser.StatementSequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(DefProgParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(DefProgParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(DefProgParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(DefProgParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CutStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCutStatement(DefProgParser.CutStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CutStatement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCutStatement(DefProgParser.CutStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(DefProgParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(DefProgParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolExpression}
	 * labeled alternative in {@link DefProgParser#expr1}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpression(DefProgParser.BoolExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolExpression}
	 * labeled alternative in {@link DefProgParser#expr1}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpression(DefProgParser.BoolExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompareExpr}
	 * labeled alternative in {@link DefProgParser#expr2}.
	 * @param ctx the parse tree
	 */
	void enterCompareExpr(DefProgParser.CompareExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompareExpr}
	 * labeled alternative in {@link DefProgParser#expr2}.
	 * @param ctx the parse tree
	 */
	void exitCompareExpr(DefProgParser.CompareExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Arithmetic1Expression}
	 * labeled alternative in {@link DefProgParser#expr3}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic1Expression(DefProgParser.Arithmetic1ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Arithmetic1Expression}
	 * labeled alternative in {@link DefProgParser#expr3}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic1Expression(DefProgParser.Arithmetic1ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Arithmetic2Expression}
	 * labeled alternative in {@link DefProgParser#expr4}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic2Expression(DefProgParser.Arithmetic2ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Arithmetic2Expression}
	 * labeled alternative in {@link DefProgParser#expr4}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic2Expression(DefProgParser.Arithmetic2ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndexedExpression}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 */
	void enterIndexedExpression(DefProgParser.IndexedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndexedExpression}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 */
	void exitIndexedExpression(DefProgParser.IndexedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralIntExpression}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterLiteralIntExpression(DefProgParser.LiteralIntExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralIntExpression}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitLiteralIntExpression(DefProgParser.LiteralIntExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralBoolExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterLiteralBoolExpr(DefProgParser.LiteralBoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralBoolExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitLiteralBoolExpr(DefProgParser.LiteralBoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralStringExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterLiteralStringExpr(DefProgParser.LiteralStringExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralStringExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitLiteralStringExpr(DefProgParser.LiteralStringExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterVariableExpression(DefProgParser.VariableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitVariableExpression(DefProgParser.VariableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InferringFunctionCall}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterInferringFunctionCall(DefProgParser.InferringFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InferringFunctionCall}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitInferringFunctionCall(DefProgParser.InferringFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(DefProgParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(DefProgParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterNegateExpr(DefProgParser.NegateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitNegateExpr(DefProgParser.NegateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MinusExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterMinusExpr(DefProgParser.MinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MinusExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitMinusExpr(DefProgParser.MinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsSetExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterIsSetExpr(DefProgParser.IsSetExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsSetExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitIsSetExpr(DefProgParser.IsSetExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AbsExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterAbsExpr(DefProgParser.AbsExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AbsExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitAbsExpr(DefProgParser.AbsExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LenExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterLenExpr(DefProgParser.LenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LenExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitLenExpr(DefProgParser.LenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubStringExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterSubStringExpr(DefProgParser.SubStringExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubStringExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitSubStringExpr(DefProgParser.SubStringExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RankExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterRankExpr(DefProgParser.RankExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RankExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitRankExpr(DefProgParser.RankExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayInitExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitExpr(DefProgParser.ArrayInitExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayInitExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitExpr(DefProgParser.ArrayInitExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayConstructExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterArrayConstructExpr(DefProgParser.ArrayConstructExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayConstructExpr}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitArrayConstructExpr(DefProgParser.ArrayConstructExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionalExpression}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(DefProgParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionalExpression}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(DefProgParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParExpression}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(DefProgParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParExpression}
	 * labeled alternative in {@link DefProgParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(DefProgParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(DefProgParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(DefProgParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#index}.
	 * @param ctx the parse tree
	 */
	void enterIndex(DefProgParser.IndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#index}.
	 * @param ctx the parse tree
	 */
	void exitIndex(DefProgParser.IndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#assignment_target}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_target(DefProgParser.Assignment_targetContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#assignment_target}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_target(DefProgParser.Assignment_targetContext ctx);
}