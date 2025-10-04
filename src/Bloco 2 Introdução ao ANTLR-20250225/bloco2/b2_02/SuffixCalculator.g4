grammar SuffixCalculator;
program:
    stat * EOF;     // Zero or more repetitions of stat

stat:
    expr? NEWLINE;  // Optative expr followed by an end-of-line

expr:
    expr expr op=('*'|'/'|'+'|'-')
    | Number;

Number: [0-9]+('.'[0-9]+)?; // fixed point real Number

NEWLINE: '\r'? '\n';
WS: [ \t]+->skip;
