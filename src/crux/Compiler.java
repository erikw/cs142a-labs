package crux;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class for the crux compiler.
 */
public class Compiler {
    /* Author details. */
    public static String studentName = "Erik Westrup";
    public static String studentID = "50471668";
    public static String uciNetID = "ewestrup";

	/**
	 * Main method that starts the compilation.
	 *
	 */
	public static void main(String[] args)
	{
        if (args.length != 1) {
			System.err.println("One argument expected. Usage:\n $ java Compiler <source_file>");
        }
        String sourceFile = args[0];
        Scanner scanner = null;

        try {
            scanner = new Scanner(new FileReader(sourceFile));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error accessing the source file: \"" + sourceFile + "\".");
            System.exit(-2);
        }

        Token t = scanner.next();
        while (false) { /* TODO t is not the EOF token */
            System.out.println(t);
            t = scanner.next();
        }
        System.out.println(t);
    }
}
