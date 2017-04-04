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
 : VAR index* ':=' numexpr '<<' numexpr '>>' numexpr
 ;

assignment_stat
 : VAR index* ':=' numexpr
 ;

if_stat
 : ('IF'|'if') boolexpr ('THEN'|'then') statement ('ELSE'|'else') statement
 ;

while_stat
 : ('WHILE'|'while') boolexpr ('DO'|'do') statement
 ;

observe_stat
 : ('OBSERVE'|'observe') boolexpr
 ;

skip_stat
 : ('SKIP'|'skip')
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