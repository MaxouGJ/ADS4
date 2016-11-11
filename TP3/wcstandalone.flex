%%
%public
%class Lexer
%standalone

%{
	int mot = 0;
	int ligne = 0;
	int char = 0;
%}

%eof{
	System.out.print("\n mots : " + mots + " caractères : " + char + " lignes : "+ lignes); 
%eof}
%%

<YYINITIAL> {
	[[:letter:]] 
	"\n" 	{lignes++; System.out.print(yytext();}
	[^] 	{char++; System.out.print(yytext();}
		 
}
