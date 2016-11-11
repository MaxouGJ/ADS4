import java.io.*;
import java.util.*;

class Parser {
	/*
	 * grammaire equivalente LL1 
	 * S -> prologue < A
	 * A -> mot Atr ASuite
	 * Atr -> epsilon | mot = string Atr
	 * ASuite -> /> | > < E
	 * E -> / mot > | A < E
	 */
    protected LookAhead1 reader;

    public Parser(LookAhead1 r) {
	reader=r;
    }


	public void term(Sym s) throws Exception{
		try{
			reader.eat(s);
		}
		catch(Exception e){
			throw new Exception("impossible de manger");
		}
	}

    public Arbre nonterm_S() throws Exception {
		if(reader.check(Sym.PROLOGUE)){
			term(Sym.PROLOGUE);
			term(Sym.LCHEVRON);
			Arbre a = nonterm_A(); 
			term(Sym.EOF);
			a.afficheElem();
			return a;
		}
		else {
			throw new Exception("En-tête manquante");
		}

    }

    public Arbre nonterm_A() throws Exception {
    	Arbre a;
    	if(reader.check(Sym.MOT)){
    		String name = reader.getValue();
    		term(Sym.MOT);
    		return new Arbre(name, nonterm_Atr(),nonterm_ASuite());	
    	}
    	else{
    		throw new Exception("Abscence de mot");
    	}
    }
    
    public List<Attribute> nonterm_Atr() throws Exception {
    	ArrayList<Attribute> attr= new ArrayList<Attribute>();
    	if(reader.check(Sym.MOT)){
    		String key = reader.getValue();
    		term(Sym.MOT);
    		term(Sym.EQ);
    		String value = reader.getValue();
    		term(Sym.STRING);
    		attr.add(new Attribute(key, value));
    		attr.addAll(nonterm_Atr());
    		return attr;
    	}
    	else if(reader.check(Sym.SLASH) || reader.check(Sym.RCHEVRON)){
    		return attr;
    	}
    	else{
    		throw new Exception("5494");
    	}
    }
    
    public List<Arbre> nonterm_ASuite() throws Exception {	
    	ArrayList<Arbre> abr = new ArrayList<Arbre>();
    	if(reader.check(Sym.SLASH)){
    		term(Sym.SLASH);
    		term(Sym.RCHEVRON);
    		return abr;
    	}
    	else if(reader.check(Sym.RCHEVRON)){
    		term(Sym.RCHEVRON);
    		term(Sym.LCHEVRON);
    		abr.addAll(nonterm_E());
    		return abr;
    	}
    	else{
    		throw new Exception("Erreur dans A suite");
    	}
    }
    
    
    public List<Arbre> nonterm_E() throws Exception {
    	ArrayList<Arbre> abr = new ArrayList<Arbre>();
    	if(reader.check(Sym.SLASH)){
    		term(Sym.SLASH);
    		term(Sym.MOT);
    		term(Sym.RCHEVRON);
    		return abr;
    	}
    	else{
    		abr.add(nonterm_A());
    		term(Sym.LCHEVRON);
    		abr.addAll(nonterm_E());
    		return abr;
    	}
    }
}
