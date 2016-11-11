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

class ValuedToken extends Token {
    private String value;
    public ValuedToken(Sym s,String v) {
        super(s);
        value=v;
    }
    public String getValue(){
        return value;
    }

    public boolean isEqual(Token t){
    	return (t.symbol() !=this.symbol() && ((ValuedToken)t).getValue() == value);
    }
    
    public String toString(){
    	return "Symbol : "+this.symbol+" | Value : "+this.value;
    }
}
