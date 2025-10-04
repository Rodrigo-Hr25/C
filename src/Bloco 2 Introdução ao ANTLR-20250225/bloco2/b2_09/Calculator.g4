grammar Calculator;

program: stat * EOF;

stat :
    'print' expr ';'                        #Print
    | 'print' 'reduce' expr ';'             #PrintReduce
    | 'read' '->' ID ';'                    #Read
    | expr '->' ID ';'                      #Assign
    ;

expr :
    expr op=( '*' | '/' | ':') expr             #ExprMulDiv
    | expr op=( '+' | '-') expr                 #ExprAddSub
    | expr '^' expr                             #ExprPow
    | '-' expr                                  #ExprNeg
    | 'read' STRING                             #ExprRead
    | 'reduce' expr                             #ExprReduce
    | '(' expr ')'                              #ExprParen
    | fraction                                  #ExprFrac
    | Integer                                   #ExprInt
    | ID                                        #ExprID
    ;

fraction: Integer '/' Integer;

ID: [a-zA-Z]+;
Integer: '-'? [0-9]+;
STRING: '"' (~["])* '"';

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\n\r]* -> skip;
