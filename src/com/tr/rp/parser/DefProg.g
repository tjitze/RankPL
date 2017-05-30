grammar DefProg;

@header {
package com.tr.rp.parser;
}

parse
 : program
 ;
 
program
 : functiondef_or_statement ';' (functiondef_or_statement ';')*  EOF
 ;

functiondef_or_statement
 : functiondef
 | statement
 ;

functiondef
 : ('DEFINE'|'define') VAR ('()' | '(' VAR (',' VAR)* ')') '{' (statement ';')* '}'
 ;

statement
 : assignment_target ':=' expression											# AssignmentStatement
 | assignment_target ':=' expression '<<' expression '>>' expression			# ChoiceAssignmentStatement
 | assignment_target ':=' '<<' expression '...' expression '>>'					# RangeChoiceStatement
 | ('IF'|'if') expression ('THEN'|'then') statement (('ELSE'|'else') statement)? # IfStatement
 | ('WHILE'|'while') expression ('DO'|'do') statement							# WhileStatement
 | ('FOR'|'for') '(' statement ';' expression ';' statement ')' statement		# ForStatement
 | ('OBSERVE'|'observe') expression												# ObserveStatement
 | ('OBSERVE-L'|'observe-l') ('(' expression ')')? expression					# ObserveLStatement
 | ('OBSERVE-J'|'observe-j') ('(' expression ')')? expression					# ObserveJStatement
 | ('SKIP'|'skip')																# SkipStatement
 | 	('NORMALLY'|'normally'|'NRM'|'nrm') ('(' expression ')')?
 		statement 
 	('EXCEPTIONALLY'|'exceptionally'|'EXC'|'exc') 
 		statement 																# RankedChoiceStatement
 |	('EITHER'|'either')
 		statement
 	(('OR'|'or') statement)+													# IndifferentChoiceStatement
 | '{' statement (';' statement)* ';'? '}'										# StatementSequence
 | ('RETURN'|'return') expression												# ReturnStatement
 | ('PRINT'|'print') expression												    # PrintStatement
 | ('CUT'|'cut') expression													    # CutStatement
 ;
 
expression : expr1;

expr1
 : expr2 (aop=('&'|'|'|'^') expr1)?	 					# BoolExpression
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

expr5
 : expr6 index*											# IndexedExpression
 ;

expr6
 : INT													# LiteralIntExpression
 | ('TRUE' | 'true') 					 				# LiteralBoolExpr
 | ('FALSE' | 'false') 					 				# LiteralBoolExpr
 | QUOTED_STRING										# LiteralStringExpr
 | ('TRUE' | 'true') 									# LiteralBoolExpr
 | ('FALSE' | 'false') 									# LiteralBoolExpr

 | variable 											# VariableExpression

 | ('infer'|'INFER') '(' VAR ('()' | '(' expression (',' expression)* ')') ')'	# InferringFunctionCall
 | VAR ('()' | '(' expression (',' expression)* ')') 	# FunctionCall

 | '!' expr5 				                 			# NegateExpr
 | '-' expr5 			     	            			# MinusExpr

 | ('ISSET'|'isSet'|'isset') '(' expression ')'    		# IsSetExpr
 | ('ABS'|'abs') '(' expression ')'         			# AbsExpr
 | ('LEN'|'len') '(' expression ')' 		 			# LenExpr
 | ('SUBSTRING'|'substring'|'subString') 
 	'(' expression ',' expression ',' expression ')' 	# SubStringExpr
 | ('RANK' | 'rank') '(' expression ')' 	 			# RankExpr

 | ('ARRAY'|'array') '(' expression (',' expression)? ')' # ArrayInitExpr
 | '[' (expression (',' expression)* )?	 ']'			# ArrayConstructExpr

 | '@'expression '?' expression ':' expression			# ConditionalExpression
 
 | '(' expression ')' 									# ParExpression
 ;

variable
 : VAR
 ;

index
 : '[' expression ']'
 ;

assignment_target
 : VAR index*
 ;
   
VAR
 : ([a-z] | [A-Z]) ( [a-z] | [A-Z] | [0-9] | '_' )*
 ;

INT
 : [0-9]+
 ;

fragment ESCAPED_QUOTE : '\\"';

QUOTED_STRING 
 : '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"'
 ;

COMMENT
 : '#' ~[\r\n]* -> skip
 ;

SPACE
 : [ \t\r\n] -> skip
 ;

OTHER
 : . 
 ;