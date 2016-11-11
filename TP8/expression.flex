%%
%public
%class Lexer
%unicode
%type Token
%line
%column

%{

%}

%yylexthrow{
	LexerException
%yylexthrow}

blank = "\n" | "\r" | " " | "\t"
int   = [1-9][0-9]*
variable = [a-z][a-zA-Z]*

%%

<YYINITIAL> {

   
   {int}	{return new IntToken(Sym.INT, Integer.parseInt(yytext()));}
   "("		{return new Token(Sym.LPAR);}
   ")"		{return new Token(Sym.RPAR);}
   "+"		{return new Token(Sym.PLUS);}
   "-"		{return new Token(Sym.MINUS);}
   "/"		{return new Token(Sym.DIV);}
   "*"		{return new Token(Sym.TIMES);}
   {blank}	{}
   <<EOF>>	{return new Token(Sym.EOF);}
   [^]		{throw new LexerException(yyline, yycolumn);}
}



