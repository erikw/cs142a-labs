package crux;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Representation of a crux token.
 */
public class Token {
	/**
	 * The token categories we're interested in distinguish.
	 */
	public static enum Category {
		KEYWORD, OPERATOR, MISC;
	}

	/**
	 * Enumeration of all possible token types in the crux language.
	 */
	public static enum Kind {
		// TODO add attribte Category.CAT?
		// Logical.
		AND("and", Token.Category.KEYWORD),
		OR("or", Token.Category.KEYWORD),
		NOT("not", Token.Category.KEYWORD),

		// Control structures.
		IF("if", Token.Category.KEYWORD),
		ELSE("else", Token.Category.KEYWORD),
		WHILE("while", Token.Category.KEYWORD),
		RETURN("return", Token.Category.KEYWORD),

		// Relational.
		GREATER_EQUAL(">=", Token.Category.OPERATOR),
		LESSER_EQUAL("<=", Token.Category.OPERATOR),
		NOT_EQUAL("!=", Token.Category.OPERATOR),
		EQUAL("==", Token.Category.OPERATOR),
		GREATER_THAN(">", Token.Category.OPERATOR),
		LESS_THAN("<", Token.Category.OPERATOR),

		// Arithmetical.
		ADD("+", Token.Category.OPERATOR),
		SUB("-", Token.Category.OPERATOR),
		MUL("*", Token.Category.OPERATOR),
		DIV("/", Token.Category.OPERATOR),

		// Types.
		INTEGER(Token.Category.MISC),
		FLOAT(Token.Category.MISC),
		TRUE("true", Token.Category.KEYWORD),
		FALSE("false", Token.Category.KEYWORD),
		LET("let", Token.Category.KEYWORD),
		VAR("var", Token.Category.KEYWORD),
		ARRAY("array", Token.Category.KEYWORD),
		FUNC("func", Token.Category.KEYWORD),

		IDENTIFIER(Token.Category.MISC),
		// TODO categorize into keywords to distinguish from identifier?
		// Misc.
		ASSIGN("=", Token.Category.OPERATOR),
		COMMA(",", Token.Category.OPERATOR),
		SEMICOLON(";", Token.Category.OPERATOR),
		COLON(":", Token.Category.OPERATOR),
		CALL("::", Token.Category.OPERATOR),
		OPEN_PAREN("(", Token.Category.OPERATOR),
		CLOSE_PAREN(")", Token.Category.OPERATOR),
		OPEN_BRACE("{", Token.Category.OPERATOR),
		CLOSE_BRACE("}", Token.Category.OPERATOR),
		OPEN_BRACKET("[", Token.Category.OPERATOR),
		CLOSE_BRACKET("]", Token.Category.OPERATOR),
		ERROR(Token.Category.MISC),
		EOF(Token.Category.MISC);
		
		// TODO complete the list of possible tokens
		
		/* Default lexeme for this Kind. */
		private String default_lexeme;

		/* The category the kind belongs to. */
		private Token.Category category;
		
		/**
		 * Construct a token type enum with a default lexemes.
		 */
		Kind(Token.Category category) {
			this("", category);
		}
		
		/**
		 * Construct a token type enum with a given lexeme.
		 */
		Kind(String lexeme, Token.Category category) {
			default_lexeme = lexeme;
		}
		
		/**
		 * Answerers the question whether this token type has a static lexeme or not.
		 * @return true if it has a static lexeme
		 * @return false otherwise.
		 */
		public boolean hasStaticLexeme() {
			return default_lexeme.length() > 0;
		}
		
		/**
		 * Report if the given test string matches the lexeme of this type.
		 */
		public boolean matches(String test) {
			return test.equals(default_lexeme);
		}

		/**
		 * Get all tokes in the give category.
		 * @param category The category to get.
		 * @return A collection of kinds in this category.
		 */
		public static Collection<Token.Kind> getCategory(Token.Category category) {
			LinkedList<Token.Kind> kinds = new LinkedList<Token.Kind>();
			for (Token.Kind kind: Token.Kind.values()) {
				if (kind.category.equals(category)) {
					kinds.add(kind);
				}
			}
			return kinds;
		}
	}
	
	/* The line number of the lexeme. */
	private int lineNum;

	/* The character position of the beginning of the lexeme. */
	private int charPos;
	
	/* The kind of token the lexeme is of. */
	Kind kind;

	/* The actual instance of the token. */
	private String lexeme = "";
	
	
	// TODO OPTIONAL: implement factory functions for some tokens, as you see fit.
	
	public static Token makeEOF(int lineNum, int charPos) {
		Token tok = new Token(lineNum, charPos);
		tok.kind = Kind.EOF;
		return tok;
	}

	/**
	 * Make an operator of kind.
	 * @param kind The kind to make.
	 * @param lineNum The line number.
	 * @param charPos The character positionl
	 * @return A token of the requested type.
	 *
	 */
	public static Token makeOperator(Token.Kind kind, int lineNum, int charPos) {
		Token token = new Token(lineNum, charPos);
		token.kind. = kindk;
		return token;
	}

	//public static Token Identifier(String name, int lineNum, int charPos);
	//public static Token Float(String num, int lineNum, int charPos);

	/**
	 * Instantiate a token with line number and char position.
	 * @param lineNum The line number the lexeme was found at.
	 * @param charPos The character position on the line the lexeme was found on.
	 */
	private Token(int lineNum, int charPos) {
		this.lineNum = lineNum;
		this.charPos = charPos;
		
		// If we don't match anything, signal error.
		this.kind = Kind.ERROR;
		this.lexeme = "No Lexeme Given";
	}
	
	/**
	 * Instantiate a token with a lexeme, line number and char position.
	 * @param lexeme The found lexeme string.
	 * @param lineNum The line number the lexeme was found at.
	 * @param charPos The character position on the line the lexeme was found on.
	 */
	public Token(String lexeme, int lineNum, int charPos) {
		this.lineNum = lineNum;
		this.charPos = charPos;
		
		// TODO Based on the given lexeme determine and set the actual kind.
		
		// If we don't match anything, signal error.
		this.kind = Kind.ERROR;
		this.lexeme = "Unrecognized lexeme: " + lexeme;
	}
	
	/**
	 * Get the line number of the lexeme.
	 * @return The line number.
	 */
	public int getLineNumber() {
		return lineNum;
	}
	
	/**
	 * Get the character position of the lexeme.
	 * @return The character position.
	 */
	public int getCharPosition() {
		return charPos;
	}

	/**
	 * Return the lexeme representing or held by this token.
	 * @return The lexeme string.
	 */
	public String getLexeme() {
		// TODO implement
		return null;
	}

	/**
	 * Get a string represenation of this token instance.
	 * @return A formatted string describing the token.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(kind.name());
		builderappend("(");
		builder.append("lineNum:").append(lineNum);
		builder.append("charPos:").append(charPos);
		builder.append(")");
		return builder.toString();
	}

	/**
	 * Query this token if it's of a given kind.
	 */
	public boolean isKind(Kind rhs) {
		//return kind == rhs;
		return kind.equals(rhs);
	}

	// TODO OPTIONAL: function to query a token about its kind
	//           boolean is(Token.Kind kind)

	// TODO OPTIONAL: add any additional helper or convenience methods
	//           that you find make for a clean design
}
