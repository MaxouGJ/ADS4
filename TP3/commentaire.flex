%%
%public
%class Lexer
%standalone

%{
	int nbChar = 0;
	int nbCom = 0;
%}

%state INCOMMENT
%state INCOMMENT2
%state STRCOMMENT

%eof{
	System.out.println("\n"+nbChar);
	System.out.println(nbCom);
	System.out.println(100*nbCom/(nbCom+nbChar)+"%");
%eof}

%%
<YYINITIAL> {
  "/*"          {nbCom += 2; nbChar += 2; yybegin(INCOMMENT);}
  [ \t]*"//"    {nbCom += 2; nbChar += 2; yybegin(INCOMMENT2);}
  "\"" 			{nbChar++; System.out.print("\""); yybegin(STRCOMMENT);}
  [^] 			{nbChar++; System.out.print(yytext());}
  
}
<INCOMMENT> {
  "*/"   {nbCom += 2; nbChar += 2; yybegin(YYINITIAL);}
  [^]    {nbCom++; nbChar++;}
}
<INCOMMENT2> {
  "\n"   {nbChar++; yybegin(YYINITIAL);}
  [^]    {nbCom++; nbChar++;}
}
<STRCOMMENT> {
	"\\\"" 	{nbChar += 2;System.out.print("\\\"");}
	"\"" 	{nbChar += 2; System.out.print("\"");	yybegin(YYINITIAL);}
	[^] 	{nbChar++; System.out.print(yytext());}
}
