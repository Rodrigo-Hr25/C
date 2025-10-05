grammar IIML;

program
    : statement* EOF
    ;

statement
    : instantiateStmt                 # Instantiate
    | placeStmt                       # Place
    | declStmt                        # Decl
    | assignStmt                      # Assign
    | forStmt                         # For
    ;

// Instanciação de variáveis
instantiateStmt
    : type size 'background' NUM
    ;

// Colocação de variáveis
placeStmt
    : 'place' shape sizeOrDimensions 'at' expr expr 'with' 'intensity' expr
    ;

// Declaração de variáveis
declStmt
    : type ID 'is' expr
    ;

// Atribuição
assignStmt
    : ID 'is' expr
    ;

// For loop statement - simplified to match the actual usage
forStmt
    : 'for' 'list' ID 'within' expr statement*
    ;

type 
    : 'number' 
    | 'string' 
    | 'image'
    | 'list' 'of' type
    ;

shape : 'circle' | 'rect' | 'cross' | 'plus' ;

size
    : 'size' (expr) 'by' (expr)        # SizeBy
    | 'radius' expr                    # Radius
    ;

sizeOrDimensions
    : size                                          # SizeSpec
    | 'width' expr 'height' expr                    # Dimensions
    | 'radius' expr                                 # RadiusSpec
    ;

expr
    : 'read' STRING                                 # ReadExpr
    | expr '+' expr                                 # AddExpr
    | expr '-' expr                                 # SubExpr
    | expr '*' expr                                 # MulExpr
    | expr '/' expr                                 # DivExpr
    | expr '%' expr                                 # ModExpr
    | expr '^' expr                                 # PowExpr
    | expr '[' expr ']'                            # IndexExpr
    | (ID | 'number') '(' (expr (',' expr)*)? ')'   # FuncCallExpr
    | '[' (expr (',' expr)*)? ']'                  # ListLiteral
    | value                                         # ValueExpr
    ;

value
    : ID
    | NUM
    | STRING
    ;

ID            : [a-zA-Z_][a-zA-Z0-9_]*;
NUM           : [0-9]+ ('.' [0-9]+)?;
STRING        : '"' ( '\\' . | ~["\\\r\n] )* '"' ;

WS            : [ \t\r\n]+ -> skip;
COMMENT       : '//' ~[\r\n]* -> skip ;
