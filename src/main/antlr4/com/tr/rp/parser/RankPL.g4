grammar RankPL;

parse
 : program
 ;
 
program
 : functiondef_or_statement ';' (functiondef_or_statement ';')*  EOF
 ;

functiondef_or_statement
 : functiondef
 | stat
 ;

functiondef
 : Define VAR ('()' | '(' VAR (',' VAR)* ')') '{' (stat ';')* '}'
 ;

stat
 : assignment_target ':=' exp							# AssignmentStatement
 | assignment_target ':=' exp '<<' exp '>>' exp			# ChoiceAssignmentStatement
 | assignment_target ':=' '<<' exp '...' exp '>>'		# RangeChoiceStatement
 | assignment_target ':=' ReadFile '(' exp ')'			# ReadFileStatement
 | assignment_target (op=('++'|'--'))					# IncDecStatement
 | If exp Then? stat (Else stat)? 						# IfStatement
 | While exp Do? stat									# WhileStatement
 | For '(' stat ';' exp ';' stat ')' stat				# ForStatement
 | Observe exp											# ObserveStatement
 | ObserveL ('(' exp ')')? exp							# ObserveLStatement
 | ObserveJ ('(' exp ')')? exp							# ObserveJStatement
 | Skip													# SkipStatement
 | Nrm ('(' exp ')')? stat (Exc stat)? 					# RankedChoiceStatement
 | Exc ('(' exp ')')? stat								# ExceptionallyStatement
 | Either stat (Or stat)+								# IndifferentChoiceStatement
 | '{' stat (';' stat)* ';'? '}'						# StatementSequence
 | Return exp											# ReturnStatement
 | Print exp											# PrintStatement
 | Cut exp												# CutStatement
 | AssertRanked '(' exp (',' exp)* ')'					# AssertRankedStatement
 | Assert exp											# AssertStatement
 | Reset												# ResetStatement
 | CurrentRank '(' assignment_target ')'				# CurrentRankStatement
 ;


exp : expr0;

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
 | '(' exp ')' 											# ParExpression
 ;

Define:			'define' | 'DEFINE';
ReadFile:		'readfile' | 'READFILE';
If: 			'if' | 'IF';
Then: 			'then' | 'THEN';
Else: 			'else' | 'ELSE';
While: 			'while' | 'WHILE';
Do: 			'do' | 'DO';
For: 			'for' | 'FOR';
Observe: 		'observe' | 'OBSERVE' | 'obs' | 'OBS';
ObserveL: 		'observe-l' | 'observe-L' | 'OBSERVE-L' | 'obs-l' | 'obs-L' | 'OBS-L';
ObserveJ: 		'observe-j' | 'observe-J' | 'OBSERVE-J' | 'obs-j' | 'obs-J' | 'OBS-J';
Skip: 			'skip' | 'SKIP';
Nrm: 			'normally' | 'NORMALLY' | 'nrm' | 'NRM';
Exc: 			'exceptionally' | 'EXCEPTIONALLY' | 'exc' | 'EXC';
Either: 		'either' | 'EITHER';
Or: 			'or' | 'OR';
Return: 		'return' | 'RETURN';
Print: 			'print' | 'PRINT';
Cut: 			'cut' | 'CUT';
Assert: 		'assert' | 'ASSERT';
AssertRanked: 	'assert-ranked' | 'ASSERT-RANKED';
Reset: 			'reset' | 'RESET';
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

OTHER
 : . 
 ;
 
