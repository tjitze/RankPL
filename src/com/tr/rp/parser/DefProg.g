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
 | if_stat
 | while_stat
 | observe_stat
 | skip_stat
 ;

ranked_choice
 : 'CHOOSE' numexpr 'NORMALLY' statement 'EXCEPTIONALLY' statement
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
 : compareexpr
 | booleanexpr
 | negateexpr
 | litboolexpr
 ;

compareexpr : '(' numexpr cop=('<' | '<=' | '>' | '>=' | '==' | '!=') numexpr ')';

booleanexpr : '(' boolexpr bop=('&' | '|') boolexpr ')';

negateexpr : '!' boolexpr;

litboolexpr : 'true' | 'false';
 
numexpr
 : arithnumexpr
 | litnumexpr
 | varnumexpr
 ;
 
arithnumexpr:  '(' numexpr aop=('+' | '-' | '/' | '*') numexpr ')';

litnumexpr : INT;

varnumexpr : VAR;
 
VAR
 : [a-z]
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