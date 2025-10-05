grammar IML;

program
    : statementWithSeparator* EOF
    ;

statementWithSeparator: statement ';'?;

statement
    : declStmt                        # Decl
    | assignStmt                      # Assign
    | listEditStmt                    # ListEdit
    | storeStmt                       # Store
    | outputStmt                      # Output
    | drawStmt                        # Draw
    | ifStmt                          # If
    | loopStmt                        # Loop
    ;

declStmt : ('list' 'of')* type ID ('is' expr)?;

assignStmt : ID 'is' expr;

listEditStmt : ID ('append' | 'remove' | 'insert' | 'replace') expr;

storeStmt: ID 'store' 'into' STRING;

outputStmt: 'output' expr;

drawStmt: 'draw' ID;

ifStmt: 'if' condExpr 'then' statement+ ('else' 'if' condExpr 'then' statement+)* ('else' statement+)? 'done';

loopStmt
    : 'for' type ID ('in' | 'within') ID 'do' statement+ 'done' # ForLoop
    | 'until' condExpr 'do' statement+ 'done'                   # UntilLoop
    ;

expr
    : 'read' STRING                                                                    # ReadExpr
    | 'load' 'from' expr                                                               # LoadExpr
    | 'run' 'from' 'read' (STRING | ID)                                                # RunExpr
    | dim = ('rows' | 'columns') 'of' expr                                             # SizeOfExpr
    | expr morphFunc 'by' expr                                                         # MorphFuncExpr
    | expr op = '.*' expr                                                              # MorphMulExpr
    | expr op = ('.+' | '.-') expr                                                     # MorphAddExpr
    | expr op = ('-*' | '|*' | '+*') expr                                              # ScaleExpr
    | expr op = ('*' | '/' | '+' | '-') expr                                           # ArithExpr
    | op = ('-' | '|' | '+' | '.-') expr                                               # UnaryExpr
    | expr '[' expr ']'                                                                # IndexExpr
    | '(' expr ')'                                                                     # ParenExpr
    | 'count' 'pixel' expr 'in' ID                                                     # CountPixelExpr
    | '[' (value (',' value)*)? ']'                                                    # ListExpr
    | type '(' expr ')'                                                                # CastExpr
    | value                                                                            # ValueExpr
    ;

type: 'image' | 'number' | 'string' | 'percentage';

value: ID | NUM | PERCENT | STRING;

morphFunc
    : 'erode' | 'dilate' | 'open' | 'close' | 'top' 'hat' | 'black' 'hat'
    ;

condExpr
    : condExpr 'or' condExpr                                                     # OrCond
    | condExpr 'and' condExpr                                                    # AndCond
    | 'not' condExpr                                                             # NotCond
    | 'any' 'pixel' ID op = ('.>' | '.<' | '.>=' | '.<=' | '.==' | '.!=') expr   # AnyPixelCond
    | 'all' 'pixel' ID op = ('.>' | '.<' | '.>=' | '.<=' | '.==' | '.!=') expr   # AllPixelCond
    | expr op = ('>' | '<' | '>=' | '<=' | '==' | '!=') expr                     # ComparisonCond
    ;

ID           : [a-zA-Z_] [a-zA-Z0-9_]* ;
NUM          : [0-9]+ ('.' [0-9]+)? ;
PERCENT      : NUM '%' ;
STRING       : '"' ( '\\' . | ~["\\\r\n] )* '"' ;

WS            : [ \t\r\n]+ -> skip ;
LINE_COMMENT  : '//' ~[\r\n]* -> skip ;
