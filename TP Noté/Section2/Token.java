class Token {
    private Sym symbol;
    
    public Token(Sym s) {
    	symbol=s;
    }
    
    public Sym symbol() {
    	return symbol;
    }
}   

class IntToken extends Token{

	private int value;
	
	public IntToken(Sym s, int v){
		super(s);
		value = v;
	} 	
	
	public int getValue(){
		return value;
	}
}

class StrToken extends Token{

	private String value;
	
	public StrToken(Sym s, String v){
		super(s);
		value = v;
	} 	
	
	public String getValue(){
		return value;
	}
}
