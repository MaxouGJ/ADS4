import java.io.*;

class Parser {
    private LookAhead1 reader;

    public Parser(LookAhead1 r) throws Exception{
		reader = r;
    }
    
    public String removeFirstChar(char c,String s) {
      /* Remove the first occurrence of c in s when there is one*/
	return s.replaceFirst(""+c,"");
    }

	/* À compléter... */
	
	public void term(Sym c) throws Exception{
		//System.out.println("term()"+c);
		try{
			reader.eat(c);
		}
		catch(Exception e){
			throw new Exception("Impossible de manger : "+c);
		}
	}
	
	public void sNonTerm() throws Exception{
		////System.out.println("sNonTerm()");
		eNonTerm();
		term(Sym.EOF);
	}
	
	public void eNonTerm() throws Exception{
		//System.out.println("eNonTerm()");
		if(reader.check(Sym.STR)){
			//System.out.println("Lecture d'un string");
			term(Sym.STR);
		}
		else if(reader.check(Sym.LPAR)){
			//System.out.println("Lecture de parenthese");
			term(Sym.LPAR);
			eNonTerm();
			oNonTerm();
			eNonTerm();
			term(Sym.RPAR);
		}
		else{
			throw new Exception("Impossible de faire E");
		}
	}
	
	public void oNonTerm() throws Exception{
		//System.out.println("oNonTerm()");
		if(reader.check(Sym.PLUS)){
			term(Sym.PLUS);
		}
		else if(reader.check(Sym.MOINS)){
			term(Sym.MOINS);
		}
		else{
			throw new Exception("Impossible de faire O");
		}
	}
}
