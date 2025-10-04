grammar gram;

program: stat+ EOF;

stat: NUMBER '-' WORD NEWLINE;

NUMBER: [0-9]+;
WORD: [a-zA-Z]+;

NEWLINE: '\r'? '\n' -> skip;
WS: [ \t]+ -> skip;
