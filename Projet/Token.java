import java.awt.Color;

class Token {
    protected Sym symbol;
    protected int line;
    
    public Token(Sym s, int l) {
    	symbol=s;
    	line=l+1;
    }
    
    public Sym symbol() {
    	return symbol;
    }
    
    public int getLine(){
    	return line;
    }
    
    public boolean isEqual(Token t){
    	return (t.symbol !=this.symbol);
    }
    
    public String toString(){
    	return "Symbol : "+this.symbol+" (line "+line+")";
    }
}

class IntToken extends Token {
    private int value;
    
    public IntToken(Sym s, int l, int v){
		super(s, l);
		this.value=v;
    }

    public IntToken(Sym s, int l, String str){
		super(s, l);
		int v=Integer.parseInt(str);
		this.value=v;
    }

    public int getValue(){
		return value;
    }
}

class VarToken extends Token{
    private String name;
    
    public VarToken(Sym s, int l, String n){
		super(s, l);
		this.name = n;
    }

    public String toString(){
		return name;
    }
}
