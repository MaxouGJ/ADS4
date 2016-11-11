import java.io.*;
import java.util.*;

class Parser {

	/*
	 Programme -> Declarations Instructions 
	 Declarations -> Var identificateur; Declarations | e
	 Instruction -> Avance Expression | Tourne  Expression | Bas Pinceau | Haut Pinceau | identificateur = Expression | Debut BlocInstruction Fin  | InstructionSi | InstructionTant | CouleurTrait Expression Expression Expression | TailleTrait Expression | Milieu | Droite | Gauche | Haut | Bas | Fond Expression Expression Expression | e
	 InstructionSi -> Si Expression Faire Instruction Sinon Instruction
	 InstructionTant -> Tant que Expression Faire Instruction
	 BlocInstruction -> Instruction; BlocInstruction | e
	 Expression -> nombre ExpressionSuite | identificateur ExpressionSuite | (Expression) ExpressionSuite
	 ExpressionSuite -> + Expression | - Expression | * Expression | / Expression | e
	 identificateur -> [a-z][a-zA-Z0-9]*
	 nombre -> [1-9][0-9]* | 0
	*/

   protected LookAhead1 reader;
   public String s;   

   public Parser(LookAhead1 r, String test) {
	   reader=r;
	   s = test;
   }
   
   public Program programme() throws Exception{
      Program p = new Program(declaration(), instruction(), s);
      reader.eat(Sym.EOF);
      return p;
   }
   
   public void term(Sym s) throws Exception{
      try{
         reader.eat(s);
      }catch(Exception e){
         System.out.println(e);
      }
	}
	
	public Declaration declaration() throws Exception{
	   if(reader.check(Sym.VAR)){
	      int line = reader.getLine();
  	      term(Sym.VAR);
	      Declaration d = new Declaration(reader.getString(), null, line);
	      term(Sym.NAME);
	      term(Sym.PT);
	      d.setNext(declaration());
	      return d;
	   }
	   else return null;
	}
   
   public Instruction instruction() throws Exception{
	   Instruction i;
	   if(reader.check(Sym.DEBUT)){
	      term(Sym.DEBUT);
	      i = bloc_Instruction();
	      term(Sym.FIN);
	      return i;
	   }else if(reader.check(Sym.AVANCE)){
  	      int line = reader.getLine();
  	      term(Sym.AVANCE);
  	      i = new Avance(expression(), null, line);
  	      return i;
  	   }else if(reader.check(Sym.TOURNE)){
  	      int line = reader.getLine();
  	      term(Sym.TOURNE);
  	      i = new Tourne(expression(), null, line);
  	      return i;
  	   }else if(reader.check(Sym.BAS)){
  	      int line = reader.getLine();
  	      term(Sym.BAS);
  	      i = new BasPinceau(null, line);
  	      return i;
  	   }else if(reader.check(Sym.HAUT)){
         int line = reader.getLine();
  	     term(Sym.HAUT);
         i = new HautPinceau(null, line);
         return i;
      }else if(reader.check(Sym.NAME)){
  	      String nom = reader.getStringValue();
  	      term(Sym.NAME);
  	      int line = reader.getLine();
  	      term(Sym.EQ);
  	      i = new Assignment(nom, expression(), null, line);
  	      return i;
  	   }else if(reader.check(Sym.SI)){
  	   	  return instruction_Si();
  	   }else if(reader.check(Sym.TANT)){
  	   	  return instruction_Tant();
  	   }else if(reader.check(Sym.TAILLE)){
  	   	  int line = reader.getLine();
  	      term(Sym.TAILLE);
  	   	  return new TailleTrait(expression(), null, line);
  	   }else if(reader.check(Sym.COULEUR)){
  	   	  int line = reader.getLine();
  	      term(Sym.COULEUR);
  	   	  return new CouleurTrait(expression(), expression(), expression(), null, line);
  	   }else if(reader.check(Sym.FOND)){
  	   	  int line = reader.getLine();
  	      term(Sym.FOND);
  	   	  return new Fond(expression(), expression(), expression(), null, line);
  	   }else if(reader.check(Sym.TOP)){
  	   	  int line = reader.getLine();
  	      term(Sym.TOP);
  	   	  return new Top(null, line);
  	   }else if(reader.check(Sym.BOTTOM)){
  	   	  int line = reader.getLine();
  	      term(Sym.BOTTOM);
  	   	  return new Bottom(null, line);
  	   }else if(reader.check(Sym.RIGHT)){
  	   	  int line = reader.getLine();
  	      term(Sym.RIGHT);
  	   	  return new Right(null, line);
  	   }else if(reader.check(Sym.LEFT)){
  	   	  int line = reader.getLine();
  	      term(Sym.LEFT);
  	   	  return new Left(null, line);
  	   }else if(reader.check(Sym.MID)){
  	   	  int line = reader.getLine();
  	      term(Sym.MID);
  	   	  return new Middle(null, line);
  	   }else{
  	      throw new ParserException(reader.getString());
  	   }
   }
   
   public Instruction bloc_Instruction() throws Exception{
      if(reader.check(Sym.DEBUT) || reader.check(Sym.AVANCE) || reader.check(Sym.TOURNE) || reader.check(Sym.BAS) || reader.check(Sym.HAUT) || reader.check(Sym.NAME) || reader.check(Sym.SI) || reader.check(Sym.TANT) || reader.check(Sym.TAILLE) || reader.check(Sym.COULEUR) || reader.check(Sym.TOP) || reader.check(Sym.BOTTOM) || reader.check(Sym.RIGHT) || reader.check(Sym.LEFT) || reader.check(Sym.MID) || reader.check(Sym.FOND)){
         Instruction i = instruction();
         term(Sym.PT);
         i.setNext(bloc_Instruction());
         return i;
      }
      else{ 
      	return null;
      }
   }
   
   public Instruction instruction_Si() throws Exception{
		int line = reader.getLine();
  	    term(Sym.SI);
		Expression e = expression();
		term(Sym.ALORS);
		Instruction si = instruction();
		Instruction sinon = null;
		if(reader.check(Sym.SINON)){
			term(Sym.SINON);
			sinon = instruction();
		}
   		return new Condition(e, si, sinon, bloc_Instruction(), line);
   }
   
   public Instruction instruction_Tant() throws Exception{
   		int line = reader.getLine();
  	    term(Sym.TANT);
  	    Expression e  = expression();
   		term(Sym.FAIRE);
   		Instruction i = instruction();
   		return new Loop(e, i, bloc_Instruction(), line);
   }
   
   public Expression expression() throws Exception{
      if(reader.check(Sym.INT)){
         Expression e = new Int(reader.getIntValue());
         term(Sym.INT);
         return expression_Suite(e);
      }
      else if(reader.check(Sym.NAME)){
      	Expression e = new Var(reader.getStringValue());
      	term(Sym.NAME);
      	return expression_Suite(e);
      }
      else if(reader.check(Sym.LPAR)){
         term(Sym.LPAR);
         Expression e = expression();
         term(Sym.RPAR);
         return expression_Suite(e);
      }
      else{
      	throw new ParserException(reader.getString());
      }
   }
   
   public Expression expression_Suite(Expression e) throws Exception {
		if(reader.check(Sym.PLUS)){
			reader.eat(Sym.PLUS);
			return new Sum(e,expression());
		}
		else if(reader.check(Sym.MINUS)){
			reader.eat(Sym.MINUS);
			return new Difference(e,expression());
		}
		else if(reader.check(Sym.TIMES)){
			reader.eat(Sym.TIMES);
			return new Product(e,expression());
		}
		else if(reader.check(Sym.DIV)){
			reader.eat(Sym.DIV);
			return new Division(e,expression());
		}
		else {
			return e;
		}
	}	
}     

