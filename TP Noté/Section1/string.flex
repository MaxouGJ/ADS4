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

%}

%yylexthrow{
	Exception
%yylexthrow}

space = "\n"|" "|"\r"|"\t"

%%

"\""[^\"]*"\""	{return token(Sym.STR);}
"("				{return token(Sym.LPAR);}
")" 			{return token(Sym.RPAR);}
"+" 			{return token(Sym.PLUS);}
"-" 			{return token(Sym.MOINS);}
{space}			{}
[^]				{throw new Exception("Erreur dans le fichier");}
<<EOF>>			{return token(Sym.EOF);} /* this executed when the end of the file is reached */

