import java.io.*;

class Main {

    public static void main(String[] args) 
      throws Exception {
    	if (args.length < 1) {
    		System.out.println("java Main <namefile>");
    		System.exit(1);
    	}
    
    	File input = new File(args[0]);
    	Reader reader = new FileReader(input);
    	Lexer lexer = new Lexer(reader);
        LookAhead1 look = new LookAhead1(lexer);
        
        Parser parser = new Parser(look);
        try {
        	Expression exp = parser.nontermS();
        	System.out.println("The expression is correct" +
                               "and its value is " + exp.eval() + ".");
        }
        catch (Exception e){
        	System.out.println("The expression is not correct.");
        	System.out.println(e);
        }
    }
}
