import java.io.*;

class Arithmetic {

    public static void main(String[] args) 
      throws Exception {
		File input = new File(args[0]);
		Reader reader = new FileReader(input);
		Lexer lexer = new Lexer(reader);
        LookAhead1 look = new LookAhead1(look);
        Parser parser = new Parser(look);
        /* À compléter... Ajouter les exceptions*/
        //int result = parser.sNonTerm();
        System.out.println(result);
    }
}
