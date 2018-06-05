grammar RankPL;

parse
 : program
 ;
 
program
 : functiondef_or_statement (functiondef_or_statement)*  EOF
 ;

functiondef_or_statement
 : functiondef
 | stat
 ;

functiondef
 : Define MEMOIZE? VAR ('()' | '(' VAR (',' VAR)* ')') stat
 ;

stat
 : term_stat ';'										# TermStat
 | open_stat											# OpenStat
 | '{' stat* '}'										# SequenceStat
 | ';'													# SkipStat
 ;

open_stat
 : If exp Then? stat (Else stat)? 						# IfStatement
 | For '(' assignment_target In exp ')' stat			# ForInStatement
 | For '(' term_stat ';' exp ';' term_stat ')' stat		# ForStatement
 | While exp Do? stat									# WhileStatement
 | Nrm ('(' exp ')')? stat (Exc stat)?					# RankedChoiceStatement
 | Exc ('(' exp ')')? stat								# ExceptionallyStatement
 | Either stat (Or stat)+								# IndifferentChoiceStatement
 ;

term_stat
 : assignment_target ':=' exp							# AssignmentStatement
 | assignment_target (op=('++'|'--'))					# IncDecStatement
 | Observe exp											# ObserveStatement
 | ObserveL ('(' exp ')')? exp							# ObserveLStatement
 | ObserveJ ('(' exp ')')? exp							# ObserveJStatement
 | Skip													# SkipStatement
 | Block												# BlockStatement
 | Return exp											# ReturnStatement
 | Print exp											# PrintStatement
 | PrintRanking exp										# PrintRankingStatement
 | Cut exp												# CutStatement
 | AssertRanked '(' exp (',' exp)* ')'					# AssertRankedStatement
 | Assert exp											# AssertStatement
 | Reset												# ResetStatement
 | Break												# BreakStatement
 | CurrentRank '(' assignment_target ')'				# CurrentRankStatement
 ;


exp : exprA;

exprA
 : expr0 ('<<' exprA '>>' exprA)?						# RankedChoiceExpression						
 ;
 
expr0
 : expr1  ('?' expr1 ':' expr1)?						# ConditionalExpression
 ;
 
expr1
 : expr2 (aop=('&'|'&&'|'|'|'||'|'^') expr1)?	 		# BoolExpression
 ;

expr2
 : expr3 (cop=('<'|'<='|'>'|'>='|'=='|'!=') expr2)? 	# CompareExpr
 ;

expr3
 : expr4 (aop=('+'|'-') expr3)?	 						# Arithmetic1Expression
 ;

expr4
 : expr5 (aop=('*'|'/'|'%') expr4)?						# Arithmetic2Expression
 ;

// TODO: should be parsed before expr6
expr5
 : expr6 index*											# IndexedExpression
 ;

expr6
 : INT													# LiteralIntExpression
 | True				 					 				# LiteralBoolExpr
 | False			 					 				# LiteralBoolExpr
 | QUOTED_STRING										# LiteralStringExpr
 | Infer '(' VAR ('()' | ('(' (exp (',' exp)*) ')')) ')'# InferringFunctionCall
 | VAR ('()' | ('(' (exp (',' exp)*) ')'))				# FunctionCall
 | Pop '(' assignment_target ')'						# PopFunctionCall
 | variable 											# VariableExpression
 | '!' expr6 				                 			# NegateExpr
 | '-' expr6 			     	            			# MinusExpr
 | '[' (exp (',' exp)* )? ']'							# ArrayConstructExpr
 | '['  exp '...' exp ']'								# ArrayRangeConstructExpr
 | '(' exp ')' 											# ParExpression
 | '<<' exp '...' exp '>>'								# RangeChoiceExpression
 ;

Define:			'define' | 'DEFINE';
If: 			'if' | 'IF';
Then: 			'then' | 'THEN';
Else: 			'else' | 'ELSE';
While: 			'while' | 'WHILE';
Do: 			'do' | 'DO';
For: 			'for' | 'FOR';
In: 			'in' | 'IN';
Observe: 		'observe' | 'OBSERVE' | 'obs' | 'OBS';
ObserveL: 		'observe-l' | 'observe-L' | 'OBSERVE-L' | 'obs-l' | 'obs-L' | 'OBS-L';
ObserveJ: 		'observe-j' | 'observe-J' | 'OBSERVE-J' | 'obs-j' | 'obs-J' | 'OBS-J';
Skip: 			'skip' | 'SKIP';
Block: 			'block' | 'BLOCK';
Nrm: 			'normally' | 'NORMALLY' | 'nrm' | 'NRM';
Exc: 			'exceptionally' | 'EXCEPTIONALLY' | 'exc' | 'EXC';
Either: 		'either' | 'EITHER';
Or: 			'or' | 'OR';
Return: 		'return' | 'RETURN';
Print: 			'print' | 'PRINT';
PrintRanking: 	'print-ranking' | 'PRINT-RANKING';
Cut: 			'cut' | 'CUT';
Assert: 		'assert' | 'ASSERT';
AssertRanked: 	'assert-ranked' | 'ASSERT-RANKED';
Reset: 			'reset' | 'RESET';
Break: 			'break' | 'BREAK';
True: 			'true' | 'TRUE';
False: 			'false' | 'FALSE';
Infer: 			'infer' | 'INFER';
CurrentRank: 	'currentrank' | 'CURRENTRANK' | 'currentRank';

empty_args:		'()' | '(' ')';

variable
 : VAR
 ;

index
 : '[' exp ']'
 ;

assignment_target
 : VAR index* 
 | multi_assignment_target
 ;

multi_assignment_target
 : '[' assignment_target (',' assignment_target)* ']'
 ;
   
VAR
 : ([a-z] | [A-Z]) ( [a-z] | [A-Z] | [0-9] | '_' )*
 ;

INT
 : [0-9]+
 ;

QUOTED_STRING 
 : '"' SCHAR* '"'
 ;

fragment
SCHAR
    :   ~["\\\r\n]
    |   ESCAPE_SEQUENCE
    |   '\\\n'   // Added line
    |   '\\\r\n' // Added line
    ;

fragment
ESCAPE_SEQUENCE
    :   '\\' ['"?abfnrtv\\]
    ;

COMMENT
 : '#' ~[\r\n]* -> skip
 ;

SPACE
 : [ \t\r\n] -> skip
 ;

MEMOIZE
 : '$'
 ;
 
OTHER
 : . 
 ;
 
