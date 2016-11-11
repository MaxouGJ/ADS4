%%
%public
%class Lexer
%unicode
%standalone

%%


<YYINITIAL>{

	"z\n" {System.out.print(yytext());}
	"s\n" {System.out.print(yytext());}
	"x\n" {System.out.print(yytext());}
	
	"bal"|"chacal"|"caranaval"|"festival"|"recital" {System.out.print(yytext()+"s");}
	
	"al\n" {System.out.println("aux");}
	
	"bleu" | "pneu" {System.out.print(yytext()+"s");}
	
	"au\n" {System.out.println("aux");}
	
	"eu\n" {System.out.println("eux");}
	
	"bijou" | "caillou" | "chou" | "joujou" | "genou" | "pou" | "hibou" {System.out.print(yytext()+"x");}
	
	"ou" {System.out.print(yytext()+"s");}
	
	"bail" |"travail"|"soupirail"|"email"|"vitrail"|"corail" {System.out.print(yytext().substring(0,yytext().length()-3)+"aux");}
	
	"ail" {System.out.print(yytext()+"s");}
	
	"oeil" {System.out.print("yeux");}
	
	"ciel" {System.out.print("cieux");}
	
	[a-r]"\n" {System.out.println(yytext().substring(0,yytext().length()-1)+"s");}
	[t-w]"\n" {System.out.println(yytext().substring(0,yytext().length()-1)+"s");}
	"y\n" {System.out.println(yytext().substring(0,yytext().length()-1)+"s");}
}
