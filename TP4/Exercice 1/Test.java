import java.io.*;

class Test {
    public static void main(String[] args) throws Exception {
    	System.out.println("Grammar :");
    	System.out.println(" F -> a   S -> F   S -> F | (F+S)");
   
    	System.out.println("Give me a word: ");
    	Reader reader = new InputStreamReader(System.in);
    	LookAhead1Reader r = new LookAhead1Reader(reader);
    	Parser p = new Parser(r);
    	try {
    		p.nonterm_T();
    		System.out.println("It is accepted.");
    	}
    	catch(ParserException e){
    		System.out.println("It is not accepted.");
    		System.out.println(e);
    	}
    }
}
