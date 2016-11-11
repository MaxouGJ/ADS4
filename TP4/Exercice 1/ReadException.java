public class ReadException extends Exception {
    
    private char expected;
    private char found;
    
    public ReadException(char expected, char found) {
	super("expected "+expected+", found "+found);
    this.expected = expected;
    this.found = found;
    }
    
    public char getExpected(){
    	return expected;
    }
    
    public char getFound(){
    	return found;
    }
}
