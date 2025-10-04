grammar TupleNum;

program: stat* EOF;
stat: (print | assignment) ';';

print: 'print' expression;
assignment: ID ':=' expression;

expression:
    op = tuple                              # TupleExpr
    | op = expression '+' expression        # AddExpr
    | op = ID                               # IdExpr
    | op = 'head' '(' expression ')'        # HeadExpr
    | op = 'tail' '(' expression ')'        # TailExpr
    | op = 'sum' '(' expression ')'         # SumExpr
    | op = 'average' '(' expression ')'     # AverageExpr
    ;

tuple: '[' NUMBER (',' NUMBER)* ']';

NUMBER: [0-9]+('.' [0-9]+)?;
ID: [a-zA-Z]+;
WS: [ \t\r\n] -> skip;
COMMENT: '#' .*? '\n' -> skip;