grammar Vector;

program: stat*EOF;
stat: (show|assignment) ';';
show: 'show' expression;
assignment: expression '->' ID;

expression:
    op = ('+'|'-') expression   # UnaryExpr
    | expression op= ('*'|'div') expression # MultDivExpr
    | expression op=('+'|'-') expression # AddSubExpr
    | '(' expression ')'    # ParentExpr
    | VECTOR # VectorExpr
    | NUMBER # NumberExpr
    | ID # idExpr
    ;

VECTOR: '[' (NUMBER(',' NUMBER)*?)? ']';
NUMBER: [0-9]+ ('.' [0-9]+)?;
ID: [a-z]+ [0-9]*;
WS: [ \t\r\n] -> skip;
COMMENT: '#' .*? '\n' -> skip;
ERROR: .;
