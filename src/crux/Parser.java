package crux;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Syntactic parser that reads a stream of tokens and builds a parse tree.
 */
public class Parser {
    /* Author details. */
    public static String studentName = "Erik Westrup";
    public static String studentID = "50471668";
    public static String uciNetID = "ewestrup";

	// Grammar Rule Reporting.
	private int parseTreeRecursionDepth = 0;

	/* The string representation of our parse tree. */
	private StringBuffer parseTreeBuffer = new StringBuffer();

	/**
	 * Build parse tree when entering a new production rule.
	 * @param nonTerminal The non terminal just entered.
	 */
	public void enterRule(NonTerminal nonTerminal) {
		String lineData = new String();
		for (int i = 0; i < parseTreeRecursionDepth; i++) {
			lineData += "  ";
		}
		lineData += nonTerminal.name();
		//System.out.println("descending " + lineData);
		parseTreeBuffer.append(lineData + "\n");
		parseTreeRecursionDepth++;
	}

	/**
	 * Build parse tree when exiting a production rule.
	 * @param nonTerminal The non terminal just exited.
	 */
	private void exitRule(NonTerminal nonTerminal) {
		parseTreeRecursionDepth--;
	}

	/**
	 * Returns a string representation of the parse tree.
	 * @return The parse tree representation.
	 */
	public String parseTreeReport() {
		return parseTreeBuffer.toString();
	}

	/* Buffer for error messages. */
	private StringBuffer errorBuffer = new StringBuffer();

	/**
	 * Report an error for an unexpected nonterminal.
	 * @param nt The expected non terminal.
	 * @return A string representing the error.
	 * pre: The unexpected token is at currentToken.
	 */
	private String reportSyntaxError(NonTerminal nt) {
		String message = "SyntaxError(" + lineNumber() + "," + charPosition() + ")[Expected one of " + nt.firstSet() + " but got " + currentToken.kind() + ".]";
		errorBuffer.append(message + "\n");
		return message;
	}

	/**
	 * Report an error for an unexpected kind.
	 * @param kind The expected kind.
	 * @return A string representing the error.
	 * pre: The unexpected kind is at currentToken.kind().
	 */
	private String reportSyntaxError(Token.Kind kind) {
		String message = "SyntaxError(" + lineNumber() + "," + charPosition() + ")[Expected " + kind + " but got " + currentToken.kind() + ".]";
		errorBuffer.append(message + "\n");
		return message;
	}

	/**
	 * Get the error report
	 * @return An erro repport.
	 */
	public String errorReport() {
		return errorBuffer.toString();
	}

	/**
	 * Query for errors.
	 * @return true if there are any error messages repported.
	 */
	public boolean hasError() {
		return errorBuffer.length() > 0;
	}

	/**
	 * A unchecked exception thrown when the parser had to quit its operation.
	 */
	private class QuitParseException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public QuitParseException(String errorMessage) {
			super(errorMessage);
		}
	}

	/* Get current tokens line number.
	 * @return The line number.
	 */
	private int lineNumber() {
		return currentToken.lineNumber();
	}

	/* Get current tokens char position.
	 * @return The char position.
	 */
	private int charPosition() {
		return currentToken.charPosition();
	}



	/* Scanner to fecth tokens from. */
	private Scanner scanner;

	/* The current token that's being processed. */
	private Token currentToken;

	/**
	 * Construct a new parser using a specified scanner.
	 * @param scanner The scanner to use.
	 */
	public Parser(Scanner scanner) {
		this.scanner = scanner;
		throw new RuntimeException("implement this");
	}

	/**
	 *	Begin the parsing.
	 */
	public void parse() {
		try {
			program();
		} catch (QuitParseException qpe) {
			errorBuffer.append("SyntaxError(" + lineNumber() + "," + charPosition() + ")");
			errorBuffer.append("[Could not complete parsing.]");
		}
	}


	// =Helper functions=

	/* Examine if the current token is of a specified kind.
	 * @param kind The kind to test for.
	 * @return ture if the current token had the specified kind.
	 * post: Advance stream: no.
	 */
	private boolean have(Token.Kind kind)
	{
		return currentToken.is(kind);
	}

	/* Examine if the current token is in the first set of the given non terminal.
	 * @param nt The non-terminal who's first set is to be checked.
	 * @return ture if the current token is in first(nt).
	 * post: Advance stream: no.
	 */
	private boolean have(NonTerminal nt)
	{
		return nt.firstSet().contains(currentToken.kind());
	}

	/* Examine if the current token is of a specified kind.
	 * @param kind The kind to test for.
	 * @return ture if the current token had the specified kind.
	 * post: Advance stream: if token matched.
	 */
	private boolean accept(Token.Kind kind)
	{
		if (have(kind)) {
			currentToken = scanner.next();
			return true;
		}
		return false;
	}	 

	/* Examine if the current token is in the first set of the given non terminal.
	 * @param nt The non-terminal who's first set is to be checked.
	 * @return ture if the current token is in first(nt).
	 * post: Advance stream: if token matched.
	 */
	private boolean accept(NonTerminal nt)
	{
		if (have(nt)) {
			currentToken = scanner.next();
			return true;
		}
		return false;
	}

	/* Examine if the current token is of a specified kind.
	 * @param kind The kind to test for.
	 * @return ture if the current token had the specified kind.
	 * @throws QuitParseException if token did not match.
	 * post: Advance stream: if token matched,
	 */
	private boolean expect(Token.Kind kind)
	{
		if (accept(kind))
			return true;
		String errorMessage = reportSyntaxError(kind);
		throw new QuitParseException(errorMessage);
		//return false;
	}

	/* Examine if the current token is in the first set of the given non terminal.
	 * @param nt The non-terminal who's first set is to be checked.
	 * @return ture if the current token is in first(nt).
	 * @throws QuitParseException if token did not match.
	 * post: Advance stream: if token matched.
	 */
	private boolean expect(NonTerminal nt)
	{
		if (accept(nt))
			return true;
		String errorMessage = reportSyntaxError(nt);
		throw new QuitParseException(errorMessage);
		//return false;
	}

	// =Grammar Rules=

	// literal := INTEGER | FLOAT | TRUE | FALSE .
	public void literal()
	{
		throw new RuntimeException("implement this");
	}

	// designator := IDENTIFIER { "[" expression0 "]" } .
	public void designator()
	{
		enterRule(NonTerminal.DESIGNATOR);

		expect(Token.Kind.IDENTIFIER);
		while (accept(Token.Kind.OPEN_BRACKET)) {
			expression0();
			expect(Token.Kind.CLOSE_BRACKET);
		}

		exitRule(NonTerminal.DESIGNATOR);
	}

	// program := declaration-list EOF .
	public void program()
	{
		throw new RuntimeException("implement all the grammar rules");
	}
}
