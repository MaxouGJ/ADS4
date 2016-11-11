%%
%public
%class Lexer
%unicode
%type Token


%{
private Token token(Sym type) {
      return new Token(type);
  }
  private StringToken token(Sym type, String value) {
      return new StringToken(type, value);
  }

%}

word = [^ \t\f\r\n]+
newline = \r|\n|\r\n

%%
{word}	    {return token(Sym.WORD,yytext());}
{newline}   {return token(Sym.LINE);}
[^]         {return token(Sym.CHAR);}
