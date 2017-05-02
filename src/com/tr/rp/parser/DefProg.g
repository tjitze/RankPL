grammar DefProg;

@header {
package com.tr.rp.parser;
}

parse
 : program
 ;
 
program
 : functiondef* statement (';' statement)* ';'? EOF
 ;

functiondef
 : ('DEFINE'|'define') variable ('()' | '(' variable (',' variable)* ')') '{' (statement ';')* ('RETURN'|'return') numexpr ';' '}'
 ;

statement
 : variable index* ':=' numexpr                           						# assignment_stat
 | variable index* ':=' '[' (numexpr ','?)* ']'           						# array_assignment_stat
 | variable index* ':=' numexpr '<<' numexpr '>>' numexpr 						# choice_assignment_stat
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
 | numexpr # NumBoolExpr
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
 | ('ABS'|'abs') '(' numexpr ')' # AbsExpr
 | ('LEN'|'len') '(' variable index* ')' # LenExpr
 | INT # LiteralNumExpr
 | variable index* # VariableNumExpr
 | ('RANK' | 'rank') '(' boolexpr ')' # RankExpr
 | variable ('()' | '(' numexpr (',' numexpr)* ')') # FunctionCall
 ;

variable
 : VAR 
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