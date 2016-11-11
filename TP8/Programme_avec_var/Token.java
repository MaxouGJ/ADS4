class Token {
    protected Sym symbol;
    public Token(Sym s) {
    	symbol=s;
    }
    public Sym symbol() {
    	return symbol;
    }
    public boolean isEqual(Token t){
    	return (t.symbol !=this.symbol);
    }
    public String toString(){
    	return "Symbol : "+this.symbol;
    }
}

class IntToken extends Token {
    private int value;
    
    public IntToken(Sym sym, int v){
    	super(sym);
    	value = v;
    }
    
    public int getValue(){
    	return value;
    }
}

class VarToken extends Token {
    private String value;
    
    public VarToken(Sym sym, String v){
    	super(sym);
    	value=v;
    }
    
    public String getValue(){
    	return value;
    }
}
