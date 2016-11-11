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
int   = [1-9][0-9]* | 0
variable = [a-z][a-zA-Z0-9]*

%%

<YYINITIAL> {

   {int}	      {return new IntToken(Sym.INT, yyline, yytext());}
   "("		      {return new Token(Sym.LPAR, yyline);}
   ")"		      {return new Token(Sym.RPAR, yyline);}
   "Var"          {return new Token(Sym.VAR, yyline);}
   {variable}	  {return new VarToken(Sym.NAME, yyline, yytext());}
   "Debut"        {return new Token(Sym.DEBUT, yyline);}
   "Fin"          {return new Token(Sym.FIN, yyline);}
   "BasPinceau"   {return new Token(Sym.BAS, yyline);}
   "HautPinceau"  {return new Token(Sym.HAUT, yyline);}
   "Avance"       {return new Token(Sym.AVANCE, yyline);}
   "Tourne"       {return new Token(Sym.TOURNE, yyline);}
   ";"            {return new Token(Sym.PT, yyline);}
   "="            {return new Token(Sym.EQ, yyline);}
   "+"	      	  {return new Token(Sym.PLUS, yyline);}  
   "-"		      {return new Token(Sym.MINUS, yyline);}
   "/"		      {return new Token(Sym.DIV, yyline);}
   "*"		      {return new Token(Sym.TIMES, yyline);}
   "Si"			  {return new Token(Sym.SI, yyline);}
   "Alors"		  {return new Token(Sym.ALORS, yyline);}
   "Sinon"		  {return new Token(Sym.SINON, yyline);}
   "Tant que"	  {return new Token(Sym.TANT, yyline);}
   "Faire"		  {return new Token(Sym.FAIRE, yyline);}
   "TailleTrait"  {return new Token(Sym.TAILLE, yyline);}
   "CouleurTrait" {return new Token(Sym.COULEUR, yyline);}
   "Haut" 		  {return new Token(Sym.TOP, yyline);}
   "Bas" 		  {return new Token(Sym.BOTTOM, yyline);}
   "Droite" 	  {return new Token(Sym.RIGHT, yyline);}
   "Gauche" 	  {return new Token(Sym.LEFT, yyline);}
   "Milieu"		  {return new Token(Sym.MID, yyline);}
   "Fond"		  {return new Token(Sym.FOND, yyline);}
   {blank}	      {}
   <<EOF>>	      {return new Token(Sym.EOF, yyline);}
   [^]		      {throw new LexerException(yyline, yycolumn);}
}



