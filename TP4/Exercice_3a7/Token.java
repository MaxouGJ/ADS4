class Token{
	
	private Sym s;
	
	public Token(Sym s){
		this.s = s;
	}
	
	public Sym getSym(){
		return s;
	}
	
	
}

class IntToken extends Token{
	
	private int value;
	
	IntToken(Sym s, int x){
		super(s);
		value = x;
	}
	
	public int getValue(){
		return value;
	}
}
