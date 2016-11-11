import java.io.*;
import java.util.*;

class Parser {
	/*
	 * S -> Exp$
	 * Exp -> int | (Exp ExpSuite)
     * ExpSuite -> + Exp | - Exp | * Exp | / Exp
	 */
    protected LookAhead1 reader;

    public Parser(LookAhead1 r) {
	   reader=r;
    }
    public Expression nontermS() 
    throws Exception {
        Expression exp = nonTermExp();
        reader.eat(Sym.EOF);
        return exp;
    }
    
    public Expression nonTermExp() throws Exception{
    	if(reader.check(Sym.INT)){
    		Int i = new Int(reader.getValue());
    		reader.eat(Sym.INT);
    		return i;
    	}
    	else if(reader.check(Sym.LPAR)){
    		reader.eat(Sym.LPAR);
    		Expression e = nonTermExpSuite(nonTermExp());
    		reader.eat(Sym.RPAR);
    		return e;
    	}
    	else{
    		throw new Exception("Probleme au niveau des exceptions");
    	}
    }
    
    public Expression nonTermExpSuite(Expression e) throws Exception{
    	if(reader.check(Sym.PLUS)){
    		reader.eat(Sym.PLUS);
    		return new Sum(e,nonTermExp());
    	}
    	else if(reader.check(Sym.MINUS)){
    		reader.eat(Sym.MINUS);
    		return new Difference(e,nonTermExp());
    	}
    	else if(reader.check(Sym.DIV)){
    		reader.eat(Sym.DIV);
    		return new Division(e,nonTermExp());
    	}
    	else if(reader.check(Sym.TIMES)){
    		reader.eat(Sym.TIMES);
    		return new Product(e,nonTermExp());
    	}
    	else{
    		throw new Exception("Probleme au niveau de ExpSuite");
    	}
    }
}
