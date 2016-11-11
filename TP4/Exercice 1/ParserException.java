public class ParserException extends Exception {
    
    private int pos;
    
    public ParserException(String s, int r) {
		super(s);
		pos = r;
    }
    
    public int getPos(){
    	return pos;
   	}
   	
    @Override
    public String getLocalizedMessage(){
    	return "\nError at position : "+pos+"\n"+super.getLocalizedMessage();
   	}
}
