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
	 * Enter a parse tree produced by the {@code array_assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterArray_assignment_stat(DefProgParser.Array_assignment_statContext ctx);
	/**
	 * Exit a parse tree produced by the {@code array_assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitArray_assignment_stat(DefProgParser.Array_assignment_statContext ctx);
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
	 * Enter a parse tree produced by the {@code CompareExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void enterCompareExpr(DefProgParser.CompareExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompareExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void exitCompareExpr(DefProgParser.CompareExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralBoolExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void enterLiteralBoolExpr(DefProgParser.LiteralBoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralBoolExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void exitLiteralBoolExpr(DefProgParser.LiteralBoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParboolExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void enterParboolExpr(DefProgParser.ParboolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParboolExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void exitParboolExpr(DefProgParser.ParboolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpr(DefProgParser.BooleanExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpr(DefProgParser.BooleanExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void enterNegateExpr(DefProgParser.NegateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void exitNegateExpr(DefProgParser.NegateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void enterLiteralNumExpr(DefProgParser.LiteralNumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void exitLiteralNumExpr(DefProgParser.LiteralNumExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void enterVariableNumExpr(DefProgParser.VariableNumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void exitVariableNumExpr(DefProgParser.VariableNumExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RankExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void enterRankExpr(DefProgParser.RankExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RankExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void exitRankExpr(DefProgParser.RankExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArithmeticNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticNumExpr(DefProgParser.ArithmeticNumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArithmeticNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticNumExpr(DefProgParser.ArithmeticNumExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void enterParNumExpr(DefProgParser.ParNumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void exitParNumExpr(DefProgParser.ParNumExprContext ctx);
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
}