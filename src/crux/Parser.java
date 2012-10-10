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
	private boolean have(Token.Kind kind) {
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
	private boolean accept(Token.Kind kind) {
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
	private boolean accept(NonTerminal nt) {
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
	private boolean expect(Token.Kind kind) {
		if (accept(kind)) {
			return true;
		}
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
	private boolean expect(NonTerminal nt) {
		if (accept(nt)) {
			return true;
		}
		String errorMessage = reportSyntaxError(nt);
		throw new QuitParseException(errorMessage);
		//return false;
	}



	// =Grammar Rules=

	/**
	 * Production for rule:
	 * literal := INTEGER | FLOAT | TRUE | FALSE .
	 */
	public void literal() {
		throw new RuntimeException("implement this");
	}

	/**
	 * Production for rule:
	 * designator := IDENTIFIER { "[" expression0 "]" } .
	 **/
	public void designator() {
		enterRule(NonTerminal.DESIGNATOR);

		expect(Token.Kind.IDENTIFIER);
		while (accept(Token.Kind.OPEN_BRACKET)) {
			expression0();
			expect(Token.Kind.CLOSE_BRACKET);
		}

		exitRule(NonTerminal.DESIGNATOR);
	}

	/**
	 * Production for rule:
	 * type := IDENTIFIER .
	 */
	public void type() {
		enterRule(NonTerminal.TYPE);
		expect(Token.Kind.IDENTIFIER);
		exitRule(NonTerminal.TYPE);
	}

	/**
	 * Production for rule:
	 * expression0 := expression1 [ op0 expression1 ] .
	 */
	public void expression0() {
		enterRule(NonTerminal.EXPRESSION0);
		do {
			expression1();

		} while (accept(NonTerminal.OP0));
		exitRule(NonTerminal.EXPRESSION0);
	}

	/**
	 * Production for rule:
	 * expression1 := expression2 { op1  expression2 } .
	 */
	public void expression1() {
		enterRule(NonTerminal.EXPRESSION1);
		do {
			expression2();

		} while (accept(NonTerminal.OP1));
		exitRule(NonTerminal.EXPRESSION1);
	}

	/**
	 * Production for rule:
	 * expression2 := expression3 { op2 expression3 } .
	 */
	public void expression2() {
		enterRule(NonTerminal.EXPRESSION2);
		do {
			expression3();

		} while (accept(NonTerminal.OP2));
		exitRule(NonTerminal.EXPRESSION2);
	}

	/**
	 * Production for rule:
	 * expression3 := "not" expression3 | "(" expression0 ")" | designator |
	 *  call-expression | literal .
	 */
	public void expression3() {
		enterRule(NonTerminal.EXPRESSION3);
		if (accept(Token.Kind.NOT)) {
			expression3();
		} else if (accept(Token.Kind.OPEN_PAREN)) {
			expression0();
			expect(Token.Kind.CLOSE_PAREN);
		} else if (have(NonTerminal.DESIGNATOR)) {
			designator();
		} else if (have(NonTerminal.CALL_EXPRESSION)) {
			call_expression();
		} else if (have(NonTerminal.LITERAL)) {
			literal();
		} else {
			// TODO does output expect error to be reported from here or from the insde the last possible alternative?
			String errorMessage = reportSyntaxError(NonTerminal.EXPRESSION3);
			throw new QuitParseException(errorMessage);
		}
		exitRule(NonTerminal.EXPRESSION3);
	}

	/**
	 * Production for rule:
	 * call-expression := "::" IDENTIFIER "(" expression-list ")" .
	 */
	public void call_expression() {
		enterRule(NonTerminal.CALL_EXPRESSION);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.OPEN_PAREN);
		expression_list();
		expect(Token.Kind.CLOSE_PAREN);
		exitRule(NonTerminal.CALL_EXPRESSION);
	}

	/**
	 * Production for rule:
	 * expression-list := [ expression0 { "," expression0 } ] .
	 */
	public void expression_list() {
		enterRule(NonTerminal.EXPRESSION_LIST);
		if (have(NonTerminal.EXPRESSION0)) {
			 do {
				expression0();
			} while (accept(Token.Kind.COMMA));
		}
		exitRule(NonTerminal.EXPRESSION_LIST);
	}

	/**
	 * Production for rule:
	 * variable-declaration := "var" IDENTIFIER ":" type ";" .
	 * 
	 */
	public void variable_declaration() {
		enterRule(NonTerminal.VARIABLE_DECLARATION);
		expect(Token.Kind.VAR);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.COLON);
		type();
		expect(Token.Kind.SEMICOLON);
		exitRule(NonTerminal.VARIABLE_DECLARATION);
	}

	/**
	 * Production for rule:
	 * array-declaration := "array" IDENTIFIER ":" type "[" INTEGER "]" { "[" INTEGER "]" } ";" .
	 */
	public void array_declaration() {
		enterRule(NonTerminal.ARRAY_DECLARATION);
		expect(Token.Kind.ARRAY);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.COLON);
		type();
		expect(Token.Kind.OPEN_BRACKET);
		expect(Token.Kind.INTEGER);
		expect(Token.Kind.CLOSE_BRACKET);
		while (accept(Token.Kind.OPEN_BRACKET)) {
			expect(Token.Kind.INTEGER);
			expect(Token.Kind.CLOSE_BRACKET);
		}
		expect(Token.Kind.SEMICOLON);
		exitRule(NonTerminal.ARRAY_DECLARATION);
	}

	/**
	 * Production for rule:
	 * function-definition := "func" IDENTIFIER "(" parameter-list ")" ":" type statement-block .
	 */
	public void function_definition() {
		enterRule(NonTerminal.FUNCTION_DEFINITION);
		expect(Token.Kind.FUNC);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.OPEN_PAREN);
		parameter_list();
		expect(Token.Kind.COLON);
		type();
		statement_block();
		exitRule(NonTerminal.FUNCTION_DEFINITION);
	}

	/**
	 * Production for rule:
	 * declaration := variable-declaration | array-declaration | function-definition .
	 */
	public void declaraion() {
		enterRule(NonTerminal.DECLARATION);
		if (have(NonTerminal.VARIABLE_DECLARATION)) {
			variable_declaration();
		} else if (have(NonTerminal.ARRAY_DECLARATION)) {
			array_declaration();
		} else {
			function_definition();
		}
		exitRule(NonTerminal.DECLARATION);
	}

	/**
	 * Production for rule:
	 * declaration-list := { declaration } .
	 */
	public void declaration_list() {
		enterRule(NonTerminal.DECLARATION_LIST);
		expect(Token.Kind.OPEN_BRACE);
		while (accept(NonTerminal.DECLARATION)) {
			declaration();
		}
		expect(Token.Kind.CLOSE_BRACE);
		exitRule(NonTerminal.DECLARATION_LIST);
	}


	/**
	 * Production for rule:
	 * assignment-statement := "let" designator "=" expression0 ";" .
	 */
	public void assignment_statement() {
		enterRule(NonTerminal.ASSIGNMENT_STATEMENT);
		// TODO continue here 1
		exitRule(NonTerminal.ASSIGNMENT_STATEMENT);
	}
	/**
	 * Production for rule:
	 * call-statement := call-expression ";" .
	 */
	public void call_statement() {
		enterRule(NonTerminal.CALL_STATEMENT);
		call_expression();
		expect(Token.Kind.SEMICOLON);
		exitRule(NonTerminal.CALL_STATEMENT);
	}

	/**
	 * Production for rule:
	 * statement := variable-declaration | call-statement | assignment-statement 
	 * | if-statement | while-statement | return-statement .
	 */
	public void statemet() {
		enterRule(NonTerminal.STATEMENT);
		if (have(NonTerminal.VARIABLE_DECLARATION)) {
			variable_declaration();
		} else if (have(NonTerminal.CALL_STATEMENT)) {
			call_statement();
		} else if (have(NonTerminal.ASSIGNMENT_STATEMENT)) {
			assignment_statement();
		} else if (have(NonTerminal.IF_STATEMENT)) {
			if_statement();
		} else if (have(NonTerminal.WHILE_STATEMENT)) {
			while_statement();
		} else {
			return_statement();
		}
		exitRule(NonTerminal.STATEMENT);
	}

	/**
	 * Production for rule:
	 * statement-list := { statement } .
	 */
	public void statement_list() {
		enterRule(NonTerminal.STATEMENT_LIST);
		while (accept(NonTerminal.STATEMENT)) {
			statement();
		}
		exitRule(NonTerminal.STATEMENT_LIST);
	}

	/**
	 * Production for rule:
	 * statement-block := "{" statement-list "}" .
	 */
	public void statement_block() {
		enterRule(NonTerminal.STATEMENT_BLOCK);
		expect(Token.Kind.OPEN_BRACE);
		statement_list();
		expect(Token.Kind.CLOSE_BRACE);
		exitRule(NonTerminal.STATEMENT_BLOCK);
	}

	/**
	 * Production for rule:
	 * program := declaration-list EOF .
	 */
	public void program() {
		enterRule(NonTerminal.PROGRAM);
		declaration_list();
		expect(Token.Kind.EOF);
		exitRule(NonTerminal.PROGRAM);
	}

	/**
	 * Production for rule:
	 * 
	 */
	public void () {
		enterRule(NonTerminal.);
		exitRule(NonTerminal.);
	}
}
