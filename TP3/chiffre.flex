%%
%public
%class Lexer
%standalone

%{
	int somme = 0;
%}

%eof{
	System.out.print("\n"+somme);
%eof}
%%

<YYINITIAL> {
	[[:digit:]] 	{somme += yycharat(0) - '0'; System.out.print(yytext());}
}
