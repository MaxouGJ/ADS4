%%
%public
%class Lexer
%unicode
%type Token
%line
%column

%{
  private Token token(Sym type) {
      return new Token(type);
  }
  private Token intToken(Sym type, int value) {
      return new IntToken(type, value);
  }
  private Token strToken(Sym type, String value) {
      return new StrToken(type, value);
  }
%}

%yylexthrow{
	Exception
%yylexthrow}

space = "\n"|" "|"\r"|"\t"

%%

"\""[^\"]*"\""	{return strToken(Sym.STR, yytext());}
[0-9]+			{return intToken(Sym.INT, Integer.parseInt(yytext()));}
"("				{return token(Sym.LPAR);}
")" 			{return token(Sym.RPAR);}
"+" 			{return token(Sym.PLUS);}
"-" 			{return token(Sym.MOINS);}
"*"				{return token(Sym.MULT);}
{space}*		{}
[^]				{throw new Exception("Erreur dans le fichier");}
<<EOF>>			{return token(Sym.EOF);} /* this executed when the end of the file is reached */

