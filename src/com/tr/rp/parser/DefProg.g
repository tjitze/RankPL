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
 : variable ':=' expression														# assignment_stat
 | variable ':=' expression '<<' expression '>>' expression						# choice_assignment_stat
 | variable ':=' '<<' expression '...' expression '>>'							# range_choice
 | ('IF'|'if') expression ('THEN'|'then') statement ('ELSE'|'else') statement	# if_stat
 | ('WHILE'|'while') expression ('DO'|'do') statement							# while_stat
 | ('OBSERVE'|'observe') expression												# Observe
 | ('OBSERVE-L'|'observe-l') '(' expression ')' expression						# ObserveL
 | ('OBSERVE-J'|'observe-j') '(' expression ')' expression						# ObserveJ
 | ('SKIP'|'skip')																# skip_stat
 | 	('NORMALLY'|'normally'|'NRM'|'nrm') ('(' expression ')')?
 		statement 
 	('EXCEPTIONALLY'|'exceptionally'|'EXC'|'exc') 
 		statement 																# ranked_choice
 |	('EITHER'|'either')
 		statement
 	(('OR'|'or') statement)+													# indifferent_choice
 | '{' statement (';' statement)* ';'? '}'										# statement_sequence
 | ('RETURN'|'return') expression												# return_statement
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
 : INT													# LiteralIntExpression
 | ('TRUE' | 'true') 					 				# LiteralBoolExpr
 | ('FALSE' | 'false') 					 				# LiteralBoolExpr
 | QUOTED_STRING										# LiteralStringExpr
 | ('TRUE' | 'true') 									# LiteralBoolExpr
 | ('FALSE' | 'false') 									# LiteralBoolExpr

 | variable 											# VariableExpression
 | VAR ('()' | '(' expression (',' expression)* ')') 	# FunctionCall

 | '!' expr5 				                 			# NegateExpr
 | '-' expr5 			     	            			# MinusExpr

 | ('ISSET'|'isSet'|'isset') '(' variable ')'    		# IsSetExpr
 | ('ABS'|'abs') '(' expression ')'         			# AbsExpr
 | ('LEN'|'len') '(' expression ')' 		 			# LenExpr
 | ('SUBSTRING'|'substring'|'subString') 
 	'(' expression ',' expression ',' expression ')' 	# SubStringExpr
 | ('RANK' | 'rank') '(' expression ')' 	 			# RankExpr

 | ('ARRAY'|'array') index* (expression)?				# ArrayInitExpr
 | '[' (expression ','?)* ']'							# ArrayConstructExpr

 | '@'expression '?' expression ':' expression			# ConditionalExpression

 | '(' expression ')' 									# ParExpression
 ;

variable
 : VAR index*
 ;

index
 : '[' expression ']'
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