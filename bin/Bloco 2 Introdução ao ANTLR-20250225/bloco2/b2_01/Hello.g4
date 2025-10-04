grammar Hello;  // Define a gramática Hello
//start : greetings | farewell ; // match greetings or farewell
start : (greetings | farewell)* EOF ; // match greetings or farewell
greetings : 'hello' ID ; // match keyword hello followed by an identifier
farewell : 'bye' ID ; // match keyword bye followed by an identifier
ID : [a-z]+ ; // match lower-case identifiers
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (windows)