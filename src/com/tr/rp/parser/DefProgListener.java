// Generated from /Users/tjitze/Documents/workspace/RankPL/src/com/tr/defaultprogramming/parser/DefProg.g by ANTLR 4.5

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
	 * Enter a parse tree produced by {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(DefProgParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(DefProgParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#rank_assign_stat}.
	 * @param ctx the parse tree
	 */
	void enterRank_assign_stat(DefProgParser.Rank_assign_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#rank_assign_stat}.
	 * @param ctx the parse tree
	 */
	void exitRank_assign_stat(DefProgParser.Rank_assign_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#nondet_assign_stat}.
	 * @param ctx the parse tree
	 */
	void enterNondet_assign_stat(DefProgParser.Nondet_assign_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#nondet_assign_stat}.
	 * @param ctx the parse tree
	 */
	void exitNondet_assign_stat(DefProgParser.Nondet_assign_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#assignment_stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_stat(DefProgParser.Assignment_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#assignment_stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_stat(DefProgParser.Assignment_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(DefProgParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(DefProgParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stat(DefProgParser.While_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stat(DefProgParser.While_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#observe_stat}.
	 * @param ctx the parse tree
	 */
	void enterObserve_stat(DefProgParser.Observe_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#observe_stat}.
	 * @param ctx the parse tree
	 */
	void exitObserve_stat(DefProgParser.Observe_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#skip_stat}.
	 * @param ctx the parse tree
	 */
	void enterSkip_stat(DefProgParser.Skip_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#skip_stat}.
	 * @param ctx the parse tree
	 */
	void exitSkip_stat(DefProgParser.Skip_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolexpr(DefProgParser.BoolexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolexpr(DefProgParser.BoolexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#compareexpr}.
	 * @param ctx the parse tree
	 */
	void enterCompareexpr(DefProgParser.CompareexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#compareexpr}.
	 * @param ctx the parse tree
	 */
	void exitCompareexpr(DefProgParser.CompareexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#booleanexpr}.
	 * @param ctx the parse tree
	 */
	void enterBooleanexpr(DefProgParser.BooleanexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#booleanexpr}.
	 * @param ctx the parse tree
	 */
	void exitBooleanexpr(DefProgParser.BooleanexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#negateexpr}.
	 * @param ctx the parse tree
	 */
	void enterNegateexpr(DefProgParser.NegateexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#negateexpr}.
	 * @param ctx the parse tree
	 */
	void exitNegateexpr(DefProgParser.NegateexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#litboolexpr}.
	 * @param ctx the parse tree
	 */
	void enterLitboolexpr(DefProgParser.LitboolexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#litboolexpr}.
	 * @param ctx the parse tree
	 */
	void exitLitboolexpr(DefProgParser.LitboolexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void enterNumexpr(DefProgParser.NumexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 */
	void exitNumexpr(DefProgParser.NumexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#arithnumexpr}.
	 * @param ctx the parse tree
	 */
	void enterArithnumexpr(DefProgParser.ArithnumexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#arithnumexpr}.
	 * @param ctx the parse tree
	 */
	void exitArithnumexpr(DefProgParser.ArithnumexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#litnumexpr}.
	 * @param ctx the parse tree
	 */
	void enterLitnumexpr(DefProgParser.LitnumexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#litnumexpr}.
	 * @param ctx the parse tree
	 */
	void exitLitnumexpr(DefProgParser.LitnumexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DefProgParser#varnumexpr}.
	 * @param ctx the parse tree
	 */
	void enterVarnumexpr(DefProgParser.VarnumexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DefProgParser#varnumexpr}.
	 * @param ctx the parse tree
	 */
	void exitVarnumexpr(DefProgParser.VarnumexprContext ctx);
}