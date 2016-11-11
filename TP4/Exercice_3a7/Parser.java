
import java.io.*;

/* Grammaire : S -> M$		M -> i | (MOM)		O -> + | - | * */
class Parser {

    protected LookAhead1 reader;

    public Parser(LookAhead1 r)

reader = r;
    }
    
     public void term (Sym c) throws IOException, ReadException ,ParserException{
		/* consume the character c */
		try{
			//c++;
			reader.eat(c);
		}
		catch(ReadException e){
			throw new ParserException("cannot reduce\""+e.getFound() + "\"\nwainting for\"" +e.getExpected()+"\"",this.c);
		}
     }	

	public void sNonTerm() throws Exception{
		mNonTerm();
		term(Sym.EOF);
	}
	
	public void mNonTerm(){
		if(reader.check(Sym.INT){
			term(Sym.INT.value);
		}
		else if(reader.check(Sym.LPAR){
			term(Sym.LPAR);
			mNonTerm();
			oNonTerm();
                        mNonTerm();
                        term(Sym.RPAR);
		}
		else{
			new IllegalException("Error");
		}
	}
        public void oNonTerm() throws Exception{
            if(reader.check(Sym.PLUS))
                term(Sym.PLUS)
            if(reader.check(Sym.MULT))
                term(Sym.MULT)
            if(reader.check(Sym.MINUS))
                term(Sym.MINUS)
        }
}
