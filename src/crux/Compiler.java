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

	/* The labs available. */
	public static enum Lab { LAB1, LAB2 };

	/* Default lab if not specified. */
	private static final Lab DEFAULT_LAB = Lab.LAB2;

	/* The current lab running. */
	private Lab currentLab;

    /**
     * Construct a default compiler.
     */
    public Compiler() {
		this(DEFAULT_LAB);
    }

    /**
     * Construct a compiler running in a special lab mode.
     */
    public Compiler(Compiler.Lab labtoRun) {
		currentLab = labtoRun;
    }

    /**
     * Change the current lab to the one specified.
     * @param newLab The lab to change to.
     */
    public void setLab(Compiler.Lab newLab) {
    	currentLab = newLab;
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

		switch(currentLab) {
			case LAB1:
				// Lab #1 with output being the tokens.
				Token token;
				do {
					token = scanner.next();
					System.out.println(token);
				} while (!token.is(Token.Kind.EOF));
				break;
			case LAB2:
				// Lab #2 that prints the parse tree.
				Parser parser = new Parser(scanner);
				parser.parse();
				if (parser.hasError()) {
					System.out.println("Error parsing file.");
					System.out.println(parser.errorReport());
					System.exit(-3);
				}
				System.out.println(parser.parseTreeReport());
				break;
			default:
				System.err.println("What lab are you working on?");
		}

	}

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

}
