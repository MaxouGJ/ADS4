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
  private IntToken intToken(Sym type, int value){
  	return new IntToken(type, value);
  }
%}

%yylexthrow{
Exception
%yylexthrow}

space = "\n"|" "|"\r"|"\t"

%%

<YYINITIAL> {
	[0-9]+ 		{return token(Sym.INT, parseInt(yytext()));}
	"(" 		{return token(Sym.LPAR);}
	")" 		{return token(Sym.RPAR);}
	"*" 		{return token(Sym.MULT);}
	"+" 		{return token(Sym.PLUS);}
	"-" 		{return token(Sym.MINUS);}
	space		{}
	[^]		{throw new Exception("Error");}
	<<EOF>>		{}
}	
