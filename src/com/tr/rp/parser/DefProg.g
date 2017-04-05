grammar DefProg;

@header {
package com.tr.rp.parser;
}

parse
 : program
 ;
 
program
 : statement (';' statement)* ';'? EOF
 ;

statement
 : VAR index* ':=' numexpr                           							# assignment_stat
 | VAR index* ':=' '[' (numexpr ','?)* ']'           							# array_assignment_stat
 | VAR index* ':=' numexpr '<<' numexpr '>>' numexpr 							# choice_assignment_stat
 | ('IF'|'if') boolexpr ('THEN'|'then') statement ('ELSE'|'else') statement     # if_stat
 | ('WHILE'|'while') boolexpr ('DO'|'do') statement                             # while_stat
 | ('OBSERVE'|'observe') boolexpr 												# Observe
 | ('OBSERVE-L'|'observe-l') '(' numexpr ')' boolexpr 							# ObserveL
 | ('OBSERVE-J'|'observe-j') '(' numexpr ')' boolexpr 							# ObserveJ
 | ('SKIP'|'skip')																# skip_stat
 | statement '<<' numexpr '>>' statement             							# ranked_choice
 | '{' statement (';' statement)* ';'? '}'										# statement_sequence
 ;

boolexpr
 : '(' boolexpr ')' # ParboolExpr
 | '!' boolexpr # NegateExpr
 | numexpr cop=('<' | '<=' | '>' | '>=' | '==' | '!=') numexpr # CompareExpr
 | boolexpr bop='&' boolexpr # BooleanExpr
 | boolexpr bop='|' boolexpr # BooleanExpr
 | boolexpr bop='^' boolexpr # BooleanExpr
 | ('TRUE' | 'true') # LiteralBoolExpr
 | ('FALSE' | 'false') # LiteralBoolExpr
 ; 
 
numexpr
 : '(' numexpr ')' # ParNumExpr
 | numexpr aop='*' numexpr # ArithmeticNumExpr
 | numexpr aop='/' numexpr # ArithmeticNumExpr
 | numexpr aop='%' numexpr # ArithmeticNumExpr
 | numexpr aop='+' numexpr # ArithmeticNumExpr
 | numexpr aop='-' numexpr # ArithmeticNumExpr
 | numexpr aop='&' numexpr # ArithmeticNumExpr
 | numexpr aop='|' numexpr # ArithmeticNumExpr
 | numexpr aop='^' numexpr # ArithmeticNumExpr
 | INT # LiteralNumExpr
 | VAR index* # VariableNumExpr
 | boolexpr '?' numexpr: numexpr # ConditionalExpr
 | ('RANK' | 'rank') '(' boolexpr ')' # RankExpr
 ;

index
 : '[' numexpr ']'
 ;
  
VAR
 : [a-z] ( [a-z] | [0-9] )*
 ;

INT
 : [0-9]+
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