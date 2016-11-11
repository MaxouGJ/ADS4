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
   {int}	      	{return new IntToken(Sym.INT, Integer.parseInt(yytext()));}
   {variable}  		{return new VarToken(Sym.VARIABLE, yytext());}
   "VAR"       		{return new Token(Sym.VAR);}
   "FOR"       		{return new Token(Sym.FOR);}
   "TIMES"     		{return new Token(Sym.TIMESDO);}
   "PRINT"     		{return new Token(Sym.PRINT);}
   ";"         		{return new Token(Sym.CONCAT);}
   "="         		{return new Token(Sym.EQ);}
   "+"		   		{return new Token(Sym.PLUS);}
   "-"	     		{return new Token(Sym.MINUS);}
   "*"		   		{return new Token(Sym.TIMES);}
   "/"		   		{return new Token(Sym.DIV);}
   "("		   		{return new Token(Sym.LPAR);}
   ")"		   		{return new Token(Sym.RPAR);}
   "{"         		{return new Token(Sym.LACC);}
   "}"         		{return new Token(Sym.RACC);}
   {blank}	   		{}
   <<EOF>>	   		{return new Token(Sym.EOF);}
   [^]		   		{throw new LexerException(yyline, yycolumn);}
}
