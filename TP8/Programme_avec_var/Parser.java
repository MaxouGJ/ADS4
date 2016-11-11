import java.io.*;
import java.util.*;

class Parser {
	/*
	 * Code -> Prog$
     * Prog -> e | Inst Prog
     * Inst -> VAR var; | var = Exp; | PRINT Exp; | FOR Exp TIMESDO {Prog}
	 * Exp -> int | (Exp ExpSuite)
     * ExpSuite -> + Exp | - Exp | * Exp | / Exp
	 */
    protected LookAhead1 reader;

    public Parser(LookAhead1 r) {
	   reader=r;
    }

    public Program nontermCode() 
    throws Exception {
        Program prog = nontermProg();
        reader.eat(Sym.EOF);
        return prog;
    }

    public Program nontermProg()
    throws Exception {

        if (reader.check(Sym.VAR) || reader.check(Sym.VARIABLE) || reader.check(Sym.PRINT) || reader.check(Sym.FOR)){
        	return new Program(nontermInst(), nontermProg());
        }
        else if(reader.check(Sym.EOF)){
        	return new Program(null,null);
        }
        else{
        	throw new Exception("erreur");
        }
    }

    public Instruction nontermInst()
    throws Exception {
    	Instruction i=null;
    	if (reader.check(Sym.VARIABLE)) {
        	String nom = reader.getStringValue();
  			reader.eat(Sym.VARIABLE);
  			reader.eat(Sym.EQ);
  			i = new Assignment(nom,nontermExp());
        }
        else if (reader.check(Sym.PRINT)) {
            reader.eat(Sym.PRINT);
            i=new Print(nontermExp());
        }
        else if (reader.check(Sym.VAR)) {
            reader.eat(Sym.VAR);
            i=new Declaration(reader.getStringValue());
            reader.eat(Sym.VARIABLE);
        }
        else if (reader.check(Sym.FOR)) {
            reader.eat(Sym.FOR);
            Expression e = nontermExp();
            reader.eat(Sym.TIMESDO);
            reader.eat(Sym.LACC);
            i= new Loop(e,nontermProg());
    		reader.eat(Sym.RACC);
        }   
		
		reader.eat(Sym.CONCAT);
        return i;
    }
  
    public Expression nontermExp() 
    throws Exception {
        Expression e=null;
        if(reader.check(Sym.INT)){
        	e = new Int(reader.getIntValue());
        	reader.eat(Sym.INT);
        }
        else if(reader.check(Sym.VARIABLE)){
        	e = new Var(reader.getStringValue());
        	reader.eat(Sym.VARIABLE);
	
        }
        
        else if(reader.check(Sym.LPAR)){
        	reader.eat(Sym.LPAR);
        	Expression eTmp = nontermExp();
        	e=nontermExpSuite(eTmp);
        	reader.eat(Sym.RPAR);
        }
        return e;
    }
    
    public Expression nontermExpSuite(Expression e) 
    throws Exception {
		if(reader.check(Sym.PLUS)){
			reader.eat(Sym.PLUS);
			return new Sum(e,nontermExp());
		}
		else if(reader.check(Sym.MINUS)){
			reader.eat(Sym.MINUS);
			return new Difference(e,nontermExp());
		}
		else if(reader.check(Sym.TIMES)){
			reader.eat(Sym.TIMES);
			return new Product(e,nontermExp());
		}
		else if(reader.check(Sym.DIV)){
			reader.eat(Sym.DIV);
			return new Division(e,nontermExp());
		}
		return null;
	}
}
