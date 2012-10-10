package crux;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintStream;
import java.util.Iterator;

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
	public static void main(String[] args) {
        if (args.length != 1) {
			System.err.println("One argument expected. Usage:\n $ crxc <source_file>");
        }
        String sourceFile = args[0];
        new Compiler().compile(sourceFile);
    }

	/**
	 * Compile the file give.
	 * @param cruxFile The file to compile.
	 */
	public void compile(String cruxFile) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new FileReader(cruxFile));
        } catch (IOException e) {
            System.err.println("Error accessing the source file: \"" + cruxFile + "\".");
			System.exit(-2);
        }

		// Lab #1 when output was the tokens.
		//Token token;
		//do {
		//	token = scanner.next();
		//	System.out.println(token);
		//} while (!token.isKind(Token.Kind.EOF));

		// Lab #2 print the parse tree.
		Parser parser = new Parser(scanner);
		parser.parse();
		if (parser.hasError()) {
			System.out.println("Error parsing file.");
			System.out.println(parser.errorReport());
			System.exit(-3);
		}
		System.out.println(parser.parseTreeReport());
	}
}
