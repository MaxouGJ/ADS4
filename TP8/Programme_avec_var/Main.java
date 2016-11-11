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
        ValueEnvironment env =new ValueEnvironment();
        Parser parser = new Parser(look);
        try {
        	Program prog = parser.nontermCode();
        	System.out.println("The code is correct");
            prog.run(env);
        }
        catch (Exception e){
        	System.out.println("The code is not correct.");
        	System.out.println(e);
        }
    }
}
