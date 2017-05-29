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
	 * Enter a parse tree produced by the {@code assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_stat(DefProgParser.Assignment_statContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_stat(DefProgParser.Assignment_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code choice_assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterChoice_assignment_stat(DefProgParser.Choice_assignment_statContext ctx);
	/**
	 * Exit a parse tree produced by the {@code choice_assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitChoice_assignment_stat(DefProgParser.Choice_assignment_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code range_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRange_choice(DefProgParser.Range_choiceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code range_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRange_choice(DefProgParser.Range_choiceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code if_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(DefProgParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by the {@code if_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(DefProgParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code while_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stat(DefProgParser.While_statContext ctx);
	/**
	 * Exit a parse tree produced by the {@code while_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stat(DefProgParser.While_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code for_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_stat(DefProgParser.For_statContext ctx);
	/**
	 * Exit a parse tree produced by the {@code for_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_stat(DefProgParser.For_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Observe}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterObserve(DefProgParser.ObserveContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Observe}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitObserve(DefProgParser.ObserveContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObserveL}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterObserveL(DefProgParser.ObserveLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObserveL}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitObserveL(DefProgParser.ObserveLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObserveJ}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterObserveJ(DefProgParser.ObserveJContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObserveJ}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitObserveJ(DefProgParser.ObserveJContext ctx);
	/**
	 * Enter a parse tree produced by the {@code skip_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSkip_stat(DefProgParser.Skip_statContext ctx);
	/**
	 * Exit a parse tree produced by the {@code skip_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSkip_stat(DefProgParser.Skip_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ranked_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRanked_choice(DefProgParser.Ranked_choiceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ranked_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRanked_choice(DefProgParser.Ranked_choiceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indifferent_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIndifferent_choice(DefProgParser.Indifferent_choiceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indifferent_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIndifferent_choice(DefProgParser.Indifferent_choiceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statement_sequence}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement_sequence(DefProgParser.Statement_sequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statement_sequence}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement_sequence(DefProgParser.Statement_sequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code return_statement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(DefProgParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code return_statement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(DefProgParser.Return_statementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code print_statement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPrint_statement(DefProgParser.Print_statementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code print_statement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPrint_statement(DefProgParser.Print_statementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cut_statement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCut_statement(DefProgParser.Cut_statementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cut_statement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCut_statement(DefProgParser.Cut_statementContext ctx);
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