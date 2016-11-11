import java.io.*;

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
    private ValuedToken token(Sym type, String v) {
      return new ValuedToken(type,v);
  }


%}

%yylexthrow{
	Exception
%yylexthrow}

blank = "\n" | "\r" | " " | "\t" 
string = \"[^\"]*\"
mot = [a-zA-Z]+


%state PROLOGUE

%%
<YYINITIAL>{
 "<?"		{yybegin(PROLOGUE);}

"<"			{return token(Sym.LCHEVRON);}
 
">"			{return token(Sym.RCHEVRON);}

{mot}		{return token(Sym.MOT,yytext());}

{string}	{return token(Sym.STRING,yytext());}
 
"="			{return token(Sym.EQ);}

"/"			{return token(Sym.SLASH);} 
 
{blank}		{}

 [^]		{throw new Exception("Lexer error at line "+ yyline + " column " + yycolumn);}
 <<EOF>>    	{return new Token(Sym.EOF);}  /*this is executed when the end of file is reached*/
}

<PROLOGUE>{
 "?>"		{yybegin(YYINITIAL); return new Token(Sym.PROLOGUE);}
 [^]		{}
}

