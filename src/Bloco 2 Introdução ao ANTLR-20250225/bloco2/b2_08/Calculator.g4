grammar Calculator;

program: stat* EOF;

stat :
    (expr | assign) ? NEWLINE;

assign :
    ID '=' expr;

expr :
    op=( '+' | '-' ) expr               #ExprUnary
    | '(' expr ')'                      #ExprParent
    | ID                                #ExprID
    | expr op=( '*' | '/' | '%' ) expr  #ExprMultDivMod
    | expr op=( '+' | '-' ) expr        #ExprAddSub
    | Integer                           #ExprInteger
    ;

ID: [a-zA-Z]+;
Integer: [0-9]+;
NEWLINE: '\r'? '\n';
WS: [ \t]+ -> skip;
COMMENT: '#' .*? '\n' -> skip;
