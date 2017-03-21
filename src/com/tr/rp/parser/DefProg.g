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
 : 'if' boolexpr 'then' statement 'else' statement
 ;

while_stat
 : 'while' boolexpr 'do' statement
 ;

observe_stat
 : 'observe' boolexpr
 ;

skip_stat
 : 'skip'
 ;
 
boolexpr
 : compareexpr
 | booleanexpr
 | negateexpr
 | litboolexpr
 ;

compareexpr : '(' numexpr cop=('<' | '<=' | '>' | '>=' | '==' | '!=') numexpr ')';

booleanexpr : '(' boolexpr bop=('&' | '|' | '^' ) boolexpr ')';

negateexpr : '!' boolexpr;

litboolexpr : 'true' | 'false';
 
 
numexpr
 : arithnumexpr
 | litnumexpr
 | varnumexpr
 ;
 
arithnumexpr:  '(' numexpr aop=('+' | '-' | '/' | '%' | '*' | '&' | '|' | '^' ) numexpr ')';

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