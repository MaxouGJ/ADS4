import java.io.*;
import javax.swing.SwingUtilities;

class Main {
    public static String test;
    public static void main(String[] args) throws Exception {
    	if (args.length < 1) {
    		System.out.println("java Main <namefile>");
    		System.exit(1);
    	}
    	String x = args[0];
		Canevas c = new Canevas(x);
    }
}
