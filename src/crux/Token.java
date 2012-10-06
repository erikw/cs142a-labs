package crux;

import java.util.Collection;
import java.util.Iterator;
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
		
		// Keywords.
		TRUE("true", Token.Category.KEYWORD),
		FALSE("false", Token.Category.KEYWORD),
		LET("let", Token.Category.KEYWORD),
		VAR("var", Token.Category.KEYWORD),
		ARRAY("array", Token.Category.KEYWORD),
		FUNC("func", Token.Category.KEYWORD),
		IF("if", Token.Category.KEYWORD),
		ELSE("else", Token.Category.KEYWORD),
		WHILE("while", Token.Category.KEYWORD),
		RETURN("return", Token.Category.KEYWORD),

		// Logical.
		AND("and", Token.Category.KEYWORD),
		OR("or", Token.Category.KEYWORD),
		NOT("not", Token.Category.KEYWORD),

		// These should not exists as they are to be recognized as IDENTIFIERs.
		//VOID("void", Token.Category.KEYWORD),
		//BOOL("bool", Token.Category.KEYWORD),
		//INT("int", Token.Category.KEYWORD),
		//FLOAT("float", Token.Category.KEYWORD),

		IDENTIFIER(Token.Category.MISC),
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
			this.category = category;
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
				if (kind.category == null) {
					System.out.println(kind.name());
				}
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
	
	
	/**
	 * Make an operator of kind.
	 * @param lineNum The line number.
	 * @param charPos The character positionl
	 * @param kind The kind to make.
	 * @return A token of the requested type.
	 *
	 */
	public static Token makeTokenFromKind(int lineNum, int charPos, Token.Kind kind) {
		return makeTokenFromKindWLexeme(lineNum, charPos, kind, "");
	}

	/**=
	 * Make an operator of kind.
	 * @param lineNum The line number.
	 * @param charPos The character positionl
	 * @param kind The kind to make.
	 * @param lexeme The lexeme found. Can be empty if non is found.
	 * @return A token of the requested type.
	 *
	 */
	public static Token makeTokenFromKindWLexeme(int lineNum, int charPos, Token.Kind kind, String lexeme) {
		Token token = new Token(lineNum, charPos);
		token.kind = kind;
		token.lexeme = lexeme;
		return token;
	}

	/**
	 * Instantiate a token with line number and char position.
	 * @param lineNum The line number the lexeme was found at.
	 * @param charPos The character position on the line the lexeme was found on.
	 */
	private Token(int lineNum, int charPos) {
		this.lineNum = lineNum;
		this.charPos = charPos;
		
		// Let factory method change these.
		this.kind = Kind.ERROR;
		this.lexeme = "No Lexeme Given";
	}
	
	/**
	 * Instantiate a token with a lexeme, line number and char position.
	 * @param lexeme The found lexeme string.
	 * @param lineNum The line number the lexeme was found at.
	 * @param charPos The character position on the line the lexeme was found on.
	 */
	private Token(String lexeme, int lineNum, int charPos) {
		this.lineNum = lineNum;
		this.charPos = charPos;
		
		// Let factory method change these.
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
		if (kind.hasStaticLexeme()) {
			return kind.default_lexeme;
		} else {
			return lexeme;
		}
	}

	/**
	 * Get a string represenation of this token instance.
	 * @return A formatted string describing the token.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(kind.name());
		switch (kind) {
			case ERROR: 
				builder.append("(");
				builder.append("Unexpected character: ");
				builder.append(lexeme);
				builder.append(")");
			break;
			case INTEGER:
			case FLOAT:
			case IDENTIFIER:
				builder.append("(");
				builder.append(lexeme);
				builder.append(")");
			break;
		}
		builder.append("(");
		builder.append("lineNum:").append(lineNum);
		builder.append(", ");
		builder.append("charPos:").append(charPos);
		builder.append(")");
		return builder.toString();
	}

	/**
	 * Query this token if it's of a given kind.
	 */
	public boolean isKind(Kind rhs) {
		return kind.equals(rhs);
	}

	/**
 	 * Try to find a token that matches the lexeme in a give category.
 	 * @param category The category of tokens to consider.
 	 * @param lexeme The lexeme to look for for.
 	 * @return A matching Kind or null.
 	 */
	public static Token.Kind matchingKind(Token.Category category, String lexeme) {
		boolean found = false;
		Collection<Token.Kind> catKinds = Token.Kind.getCategory(category);
		Iterator<Token.Kind> iterator = catKinds.iterator();
		Token.Kind curKind = null;
		while (!found && iterator.hasNext()) {
			curKind = iterator.next();
			if (curKind.matches(lexeme)) {
				found = true;
			}
		}
		return found ? curKind : null;
	}
}
