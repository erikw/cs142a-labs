package crux;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Main class for the crux compiler.
 */
public class Compiler {
    /* Author details. */
    public static String studentName = "Erik Westrup";
    public static String studentID = "50471668";
    public static String uciNetID = "ewestrup";

    /* Usage instructions. */
	private static final String USAGE_DESCRIPTION = "Baffel blag? Usage:\n$ crxc [-l<1-6>] <source_file>";

	/**
	 * The labs available.
	 */
	public static enum Lab { LAB1, LAB2, LAB3, LAB4, LAB5, LAB6 };

	/* Default lab if not specified. */
	private static final Lab DEFAULT_LAB = Lab.LAB6;

	/* A mapping from integers to enum constants. */
	private static final Map<Integer, Compiler.Lab> labLookup = new HashMap<Integer, Compiler.Lab>() {
		private static final long serialVersionUID = 1L;
		{
			put(1, Compiler.Lab.LAB1);
			put(2, Compiler.Lab.LAB2);
			put(3, Compiler.Lab.LAB3);
			put(4, Compiler.Lab.LAB4);
			put(5, Compiler.Lab.LAB5);
			put(6, Compiler.Lab.LAB6);
		}
	};

	/* The current lab running. */
	public static Lab currentLab;

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
	 * @param sourceFilename The file to compile.
	 */
	public void compile(String sourceFilename) {
        Scanner scanner = null;
        Parser parser;
        ast.Command syntaxTree;
        try {
            scanner = new Scanner(new FileReader(sourceFilename));
        } catch (IOException e) {
            System.err.println("Error accessing the source file: \"" + sourceFilename + "\"");
			System.exit(-2);
        }
		switch (currentLab) {
			case LAB1:
				// Lab #1 with output being the tokens.
				Token token;
				do {
					token = scanner.next();
					System.out.println(token);
				} while (!token.is(Token.Kind.EOF));
				break;
			case LAB2:
			case LAB3:
				parser = new Parser(scanner);
				parser.parse();
				if (parser.hasError()) {
					System.out.println("Error parsing file.");
					System.out.println(parser.errorReport());
					System.exit(-3);
				}
				switch (currentLab) {
					case LAB2:
						// Lab #2 that prints the parse tree.
						System.out.println(parser.parseTreeReport());
						break;
					case LAB3:
        				// Lab #3 that builds builds a symbol table.
        				System.out.println("Crux program successfully parsed.");
						break;
				}
				break;
			case LAB4:
        		parser = new Parser(scanner);
        		syntaxTree = parser.parse();
        		if (parser.hasError()) {
            		System.out.println("Error parsing file " + sourceFilename);
            		System.out.println(parser.errorReport());
            		System.exit(-3);
				}
        		ast.PrettyPrinter prettyPrinter = new ast.PrettyPrinter();
        		syntaxTree.accept(prettyPrinter);
        		System.out.println(prettyPrinter.toString());
        		break;
			case LAB5:
			case LAB6:
        		parser = new Parser(scanner);
        		syntaxTree = parser.parse();
        		if (parser.hasError()) {
            		System.out.println("Error parsing file.");
            		System.out.println(parser.errorReport());
            		System.exit(-3);
        		}
        		types.TypeChecker typeChecker = new types.TypeChecker();
        		typeChecker.check(syntaxTree);
        		if (typeChecker.hasError()) {
            		System.out.println("Error type-checking file.");
            		System.out.println(typeChecker.errorReport());
            		System.exit(-4);
        		}
        		switch(currentLab) {
					case LAB5:
        				System.out.println("Crux Program has no type errors.");
        				break;
        			case LAB6:
        				mips.CodeGen codeGen = new mips.CodeGen(typeChecker);
        				codeGen.generate(syntaxTree);
        				if (codeGen.hasError()) {
            				System.out.println("Error generating code for file " + sourceFilename);
            				System.out.println(codeGen.errorReport());
            				System.exit(-5);
        				}

        				String asmFilename = sourceFilename.replace(".crx", ".asm");
        				try {
            				mips.Program prog = codeGen.getProgram();
            				File asmFile = new File(asmFilename);
            				PrintStream ps = new PrintStream(asmFile);
            				prog.print(ps);
            				ps.close();
        				} catch (IOException e) {
            				e.printStackTrace();
            				System.err.println("Error writing assembly file: \"" + asmFilename + "\"");
            				System.exit(-6);
        				}
        				break;
        		}
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
        String sourceFile = null;
        Lab lab = null;
        if (args.length == 1) {
        	sourceFile = args[0];
        } else if (args.length == 2) {
			Matcher matcher  = Pattern.compile("-l\\s?([1-6])").matcher(args[0]);;
			if (matcher.matches()) {
				String labNumStr = matcher.group(1);
				int labNum = Integer.valueOf(labNumStr);
				lab = labLookup.get(labNum);
				if (lab == null) {
					System.err.println("Unspported lab.");
					System.exit(1);
				}
			} else {
				System.err.println(USAGE_DESCRIPTION) ;
				System.exit(1);
			}
        	sourceFile = args[1];
        } else {
			System.err.println(USAGE_DESCRIPTION);
			System.exit(1);
        }
    	((lab != null) ? new Compiler(lab) : new Compiler()).compile(sourceFile);
    }
}
