grammar DefProg;

@header {
package com.tr.rp.parser;
}

parse
 : program
 ;
 
program
 : (statement ';')+ EOF
 ;

statement
 : ranked_choice
 | assignment_stat
 | choice_assignment_stat
 | if_stat
 | while_stat
 | observe_stat
 | skip_stat
 ;

ranked_choice
 : '{' statement '}' '<<' numexpr '>>' '{' statement '}'
 ;

choice_assignment_stat
 : VAR ':=' numexpr '<<' numexpr '>>' numexpr
 ;

assignment_stat
 : VAR ':=' numexpr
 ;

if_stat
 : 'IF' boolexpr 'THEN' statement 'ELSE' statement
 ;

while_stat
 : 'WHILE' boolexpr 'DO' statement
 ;

observe_stat
 : 'OBSERVE' boolexpr
 ;

skip_stat
 : 'SKIP'
 ;
 
boolexpr
 : '(' boolexpr ')' # ParboolExpr
 | '!' boolexpr # NegateExpr
 | numexpr cop=('<' | '<=' | '>' | '>=' | '==' | '!=') numexpr # CompareExpr
 | boolexpr bop='&' boolexpr # BooleanExpr
 | boolexpr bop='|' boolexpr # BooleanExpr
 | boolexpr bop='^' boolexpr # BooleanExpr
 | 'true' # LiteralBoolExpr
 | 'false' # LiteralBoolExpr
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
 | VAR # VariableNumExpr
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