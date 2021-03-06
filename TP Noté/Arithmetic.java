import java.io.*;

class Arithmetic {

    public static void main(String[] args) 
      throws Exception {
    	if (args.length <1) {
    		System.out.println("java Arithmetic <namefile>");
    		System.exit(1);
    	}
    
    	File input = new File(args[0]);
    	Reader reader = new FileReader(input);
    	Lexer lexer = new Lexer(reader);
        LookAhead1 look = new LookAhead1(lexer);
        Parser parser = new Parser(look);
        try {
        	parser.sNonTerm();
        	System.out.println("The file is correct");
        }
        catch (Exception e){
        	System.out.println("The file is not correct");
        	System.out.println(e);
        }
    }
}
