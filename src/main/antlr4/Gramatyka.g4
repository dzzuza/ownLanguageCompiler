grammar Gramatyka;

statement : (ifs
    |fors
    |whiles
    |prints
    |assignment)*;

ifs : ifBlock (elseIfBlock)* (elseBlock)? 'stop';

fors : 'dla 'Leftparenthesis integer Colon integer Rightparenthesis statement 'stop';

whiles : 'dopoki' Leftcurly conditionalS Rightcurly statement'stop' ;

prints : 'wypisz ' Til string;

assignment : intVar '->' operation Semicolon
    | booleanVar '->' (conditionalS
                      |booleanVar
                      | True
                      | False) Semicolon;

ifBlock : 'jesli ' Leftbracket conditionalS Rightbracket statement ;
elseIfBlock : 'albogdy ' Leftbracket conditionalS Rightbracket statement ;
elseBlock : 'wprzeciwnymrazie ' statement;
conditionalS : conditional | booleanVar ;
conditional : operation operator operation;

operator : op=(LT
    | GT
    | Equals);

operation : plus
    | minus
    | multiplication
    | division
    | integer
    | intVar ;

intVar : 'cal 'string;
booleanVar : 'log 'string;
integer: (Number)+;
string: (Letter)+;

plus : integer ' plus ' integer;
minus : integer ' minus ' integer;
multiplication : integer ' razy ' integer;
division : integer ' przez ' integer;

GT : '>';
LT : '<';
Equals : '==';
Leftparenthesis : '(';
Rightparenthesis : ')';
Leftbracket : '[';
Rightbracket : ']';
Leftcurly : '{';
Rightcurly : '}';
Til : '~';
Colon : ':';
Semicolon : ';';
Zero : [0];
Number : [0-9];
Letter : 'a'..'z' | 'A'..'Z' | '_';
True : 'true';
False: 'false';
WS:[ \t\r\n]+ -> skip ;