import java.io.*;

/* simple LL(1) parser for the grammar :   */
/* F -> a    T -> T\n    T -> (F+T)   T -> F   */

class Parser {

    protected LookAhead1Reader reader;
	public int c;
	

    public Parser(LookAhead1Reader r) {
	reader=r;
	c=1;
    }

    public void term (char c) throws IOException, ReadException ,ParserException{
	/* consume the character c */
	try{
	c++;
	reader.eat(c);
    }catch(ReadException e){
    	throw new ParserException("cannot reduce\""+e.getFound() + "\"\nwainting for\"" +e.getExpected()+"\"",this.c);
    }
       }	

    public void nonterm_T()
	throws IOException, IllegalArgumentException,
	       ReadException, ParserException {
	/* parse a word generated from nonterminal T */

	if(reader.check('a')){
		
	   this.nonterm_F();
	this.term('\n');    	
		} 
		else if (reader.check('(')) {
	    
	    	this.term('(');
	  
	    	this.nonterm_F();
	    
	    	this.term('+');
	    	
	    	this.nonterm_S();
	    
	    	this.term(')');
	    		this.term('\n');  
	
	}else{
    	throw new ParserException("cannot reduce T\n",this.c);
    }
    }


    public void nonterm_S()
	throws IOException, IllegalArgumentException,
	       ReadException, ParserException {
	/* parse a word generated from nonterminal S */
		if (reader.check('a')) {
		    this.nonterm_F();
	    	
		} 
		else if (reader.check('(')) {
	    
	    	this.term('(');
	  
	    	this.nonterm_F();
	    
	    	this.term('+');
	    	
	    	this.nonterm_S();
	    
	    	this.term(')');
	
		} 
		else {
	    	throw new ParserException("cannot reduce S",c);
		}
    }

    public void nonterm_F() throws IOException, ReadException, ParserException {
	/* parse a word generated from nonterminal F */
		if(reader.check('a')){
			this.term('a');
		}
   	 	else{
   	 		throw new ParserException("cannot reduce F\nwainting for a",this.c);
   	 	}
    }
}
