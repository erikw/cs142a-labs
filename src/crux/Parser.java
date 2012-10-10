package crux;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {
	public static String studentName = "TODO: Your Name";
	public static String studentID = "TODO: Your 8-digit id";
	public static String uciNetID = "TODO: uci-net id";

	// Grammar Rule Reporting ==========================================
	private int parseTreeRecursionDepth = 0;
	private StringBuffer parseTreeBuffer = new StringBuffer();

	public void enterRule(NonTerminal nonTerminal) {
		String lineData = new String();
		for (int i = 0; i < parseTreeRecursionDepth; i++)
		{
			lineData += "  ";
		}
		lineData += nonTerminal.name();
		//System.out.println("descending " + lineData);
		parseTreeBuffer.append(lineData + "\n");
		parseTreeRecursionDepth++;
	}

	private void exitRule(NonTerminal nonTerminal)
	{
		parseTreeRecursionDepth--;
	}

	public String parseTreeReport()
	{
		return parseTreeBuffer.toString();
	}

	// Error Reporting ==========================================
	private StringBuffer errorBuffer = new StringBuffer();

	private String reportSyntaxError(NonTerminal nt)
	{
		String message = "SyntaxError(" + lineNumber() + "," + charPosition() + ")[Expected one of " + nt.firstSet() + " but got " + currentToken.kind() + ".]";
		errorBuffer.append(message + "\n");
		return message;
	}

	private String reportSyntaxError(Token.Kind kind)
	{
		String message = "SyntaxError(" + lineNumber() + "," + charPosition() + ")[Expected " + kind + " but got " + currentToken.kind() + ".]";
		errorBuffer.append(message + "\n");
		return message;
	}

	public String errorReport()
	{
		return errorBuffer.toString();
	}

	public boolean hasError()
	{
		return errorBuffer.length() != 0;
	}

	private class QuitParseException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
		public QuitParseException(String errorMessage) {
			super(errorMessage);
		}
	}

	private int lineNumber()
	{
		return currentToken.lineNumber();
	}

	private int charPosition()
	{
		return currentToken.charPosition();
	}

	// Parser ==========================================
	private Scanner scanner;
	private Token currentToken;

	public Parser(Scanner scanner)
	{
		throw new RuntimeException("implement this");
	}

	public void parse()
	{
		try {
			program();
		} catch (QuitParseException q) {
			errorBuffer.append("SyntaxError(" + lineNumber() + "," + charPosition() + ")");
			errorBuffer.append("[Could not complete parsing.]");
		}
	}

	// Helper Methods ==========================================
	private boolean have(Token.Kind kind)
	{
		return currentToken.is(kind);
	}

	private boolean have(NonTerminal nt)
	{
		return nt.firstSet().contains(currentToken.kind());
	}

	private boolean accept(Token.Kind kind)
	{
		if (have(kind)) {
			currentToken = scanner.next();
			return true;
		}
		return false;
	}	 

	private boolean accept(NonTerminal nt)
	{
		if (have(nt)) {
			currentToken = scanner.next();
			return true;
		}
		return false;
	}

	private boolean expect(Token.Kind kind)
	{
		if (accept(kind))
			return true;
		String errorMessage = reportSyntaxError(kind);
		throw new QuitParseException(errorMessage);
		//return false;
	}

	private boolean expect(NonTerminal nt)
	{
		if (accept(nt))
			return true;
		String errorMessage = reportSyntaxError(nt);
		throw new QuitParseException(errorMessage);
		//return false;
	}

	// Grammar Rules =====================================================

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
