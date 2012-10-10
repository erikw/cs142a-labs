package crux;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.OperationNotSupportedException;

//import java.io.IOException;

/**
 * A scanner that reads crux source text and produces as stream of tokens.
 */
public class Scanner implements Iterable<Token> {
    /* Represents End of File. */
    private static final short EOF = -1;

    /* Represents Beginning of File. */
    private static final short BOF = -2;

    /* Means that no character is unread. */
    private static final short NO_UNREAD_CHAR = -2;

    /* Author details. */
    public static String studentName = "Erik Westrup";
    public static String studentID = "50471668";
    public static String uciNetID = "ewestrup";

	/* Current line count. */
	private int lineNum = 1;

	/* Character offset for current line. */
	private int charPos = 0;

	/* Contains the next char (-1 == EOF). */
	private int nextChar = BOF;

	/* Keep the current nextChar one time. Implements a puschback/unread feature. */
	private boolean holdNextChar = false;

	/* Input source. */
	private Reader input;

	/**
	 * Construct a scanner from a reader.
	 * @param reader The reader to use a input source.
	 */
	Scanner(Reader reader) {
		input = reader;
	}

	/**
	 * Unread current nextChar.
	 * post: lineNum and charPos still corresponds to the unread char. Why? 
	 * Because if we push back an \n it becomes messy to reverse these 
	 * variables as we have to record more information.
	 */
	private void unreadChar() {
		holdNextChar = true;
	}

	/**
	 * Read the next charcter and maintain stuff.
	 */
	private void readChar() {
		if (holdNextChar) {
			holdNextChar = false;
		} else {
			try {
				nextChar = input.read();
			} catch (IOException ioe) {
				System.err.println("Caught IOException in input stream.");
				System.exit(1);
			}
			switch (nextChar) {
				case -1:
					nextChar = EOF;
					++charPos;
					break;	
				case '\n':
					++lineNum;
					charPos = 0;
					break;
				default:
				++charPos;
			}
		}
		//System.out.println("next char is \"" + (char) nextChar  + "\"");
	}


	/**
	 * The states we can be in when parsing.
	 */
	private static enum State {
		BEGINNING, IDENTIFIER, DIGIT, LESS_THAN, GREATER_THAN, ASSIGN, BANG, COLON, FLOAT, SLASH, COMMENT;
	}

	/**
	 * Get the next available Token.
	 * @return Token The next Token or EOF-token if input stream contains to more tokens.
	 *  pre: call assumes that nextChar is already holding an unread character (if this is not the first call)
	 *  post: return leaves nextChar containing an untokenized character.
	 */
	public Token next() {
		Token token = null;
		StringBuilder lexemeBuilder = new StringBuilder();
		State state = State.BEGINNING;
		if (nextChar == BOF) {
			// Don't want to block in constructor.
			readChar();
		}
		int lexBegLineNum = 0;
		int lexBegCharPos = 0;
		boolean firstLoop = true;
		while (token == null) {
			if (firstLoop) {
				firstLoop = false;
			} else {
				readChar();
			}
			if (nextChar == EOF) { // EOF can come anywhere in the stream.
				if (state != State.BEGINNING) {
					token = Token.makeTokenFromKind(lineNum, charPos, Token.Kind.ERROR);
				} else {
					//token = Token.makeEOF(lineNum, charPos);
					token = Token.makeTokenFromKind(lineNum, charPos, Token.Kind.EOF);
				}
				continue;
			}
			switch (state) {
				case BEGINNING:
				 	if (Character.isWhitespace((char) nextChar)) {
						//System.out.println("skipping ws");
						continue;
					}

					lexBegLineNum = lineNum;
					lexBegCharPos = charPos;
					lexemeBuilder.append((char) nextChar);
					if (Token.Kind.LESS_THAN.matches(String.valueOf((char) nextChar))) {
						state = State.LESS_THAN;
					} else if (Token.Kind.GREATER_THAN.matches(String.valueOf((char) nextChar))) {
						state = State.GREATER_THAN;
					} else if (Token.Kind.ASSIGN.matches(String.valueOf((char) nextChar))) {
						//System.out.println("in assign and charpos=" +charPos);
						state = State.ASSIGN;
					} else if (nextChar == '!') {
						state = State.BANG;
					} else if (nextChar == ':') {
						state = State.COLON;
					} else if (nextChar == '/') {
						state = State.SLASH;
					} else {
						Token.Kind matchKind = Token.matchingKind(Token.Category.OPERATOR, lexemeBuilder.toString());
						if (matchKind != null) {
							token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, matchKind);
						} else if (matchesIdentifier(true, nextChar)) { // Character.isLetter((char) nextChar) // Will allow non ASCII chars.
							state = State.IDENTIFIER;
						} else if (nextChar >= '0' && nextChar <= '9') {
							state = State.DIGIT;
						} else {
							token = Token.makeTokenFromKindWLexeme(lexBegLineNum, lexBegCharPos, Token.Kind.ERROR, Character.toString((char) nextChar));
						}
					}
					break;
				case IDENTIFIER:
					if (matchesIdentifier(false, nextChar)) {
						lexemeBuilder.append((char) nextChar);
					} else {
						unreadChar();
						String identifier = lexemeBuilder.toString();
						Token.Kind matchKind = Token.matchingKind(Token.Category.KEYWORD, lexemeBuilder.toString());
						Token.Kind kindToUse = null;
						if (matchKind == null) {
							kindToUse = Token.Kind.IDENTIFIER;
						} else {
							kindToUse = matchKind;
						}
						//token = Token.makeIdentifier(lexBegLineNum, lexBegCharPos, identifier);
						token = Token.makeTokenFromKindWLexeme(lexBegLineNum, lexBegCharPos, kindToUse, identifier);
					}
					break;
				case DIGIT:
					if (nextChar == '.') {
						lexemeBuilder.append((char) nextChar);
						state = State.FLOAT;
					} else if (nextChar >= '0' && nextChar <= '9') {
						lexemeBuilder.append((char) nextChar);
					} else {
						unreadChar();
						String integer = lexemeBuilder.toString();
						token = Token.makeTokenFromKindWLexeme(lexBegLineNum, lexBegCharPos, Token.Kind.INTEGER, integer);
					}
					break;
				case FLOAT:
					if (nextChar >= '0' && nextChar <= '9') {
						lexemeBuilder.append((char) nextChar);
					} else {
						unreadChar();
						String floating = lexemeBuilder.toString();
						token = Token.makeTokenFromKindWLexeme(lexBegLineNum, lexBegCharPos, Token.Kind.FLOAT, floating);
					}
					break;
				case LESS_THAN:
					if (nextChar == '=') {
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.LESSER_EQUAL);
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.LESS_THAN);
					}
					break;
				case GREATER_THAN:
					if (nextChar == '=') {
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.GREATER_EQUAL);
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.GREATER_THAN);
					}
					break;
				case ASSIGN:
					if (nextChar == '=') {
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.EQUAL);
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.ASSIGN);
					}
					break;
				case BANG:
					if (nextChar == '=') {
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.NOT_EQUAL);
					} else {
						unreadChar();
						//token = Token.makeError(lexBegLineNum, lexBegCharPos, (char) nextChar);
						token = Token.makeTokenFromKindWLexeme(lexBegLineNum, lexBegCharPos, Token.Kind.ERROR,Character.toString('!'));
					}
					break;
				case COLON:
					if (nextChar == ':') {
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.CALL);
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.COLON);
					}
					break;
				case SLASH:
					if (nextChar == '/') {
						state = State.COMMENT;
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(lexBegLineNum, lexBegCharPos, Token.Kind.DIV);
					}
					break;
				case COMMENT:
					if (nextChar == '\n') {
						state = State.BEGINNING;
						lexemeBuilder.delete(0, (lexemeBuilder.length()));
						unreadChar();
					}
					break;
				default:
					System.err.println("defalt label in next switch. Should not be here!");
					System.exit(1);
			}
		}
		//System.out.println("anropslut");
		readChar(); // Make sure nextChar contains the next input to use.
		return token;
	}

	/**
 	 * Short hand for testing if the supplied character matces the caracters in an identifier.
 	 * @param firstChar If we're testing the first char in a string we think is an identifier.
 	 * @param testChar The character to test.
 	 * @return Boolean answer for the question.
 	 */
	private boolean matchesIdentifier(boolean firstChar, int testChar) {
		return ((testChar >= 'a' && testChar <= 'z') || (testChar >= 'A' && testChar <= 'Z') || testChar == '_') ?
			true : (!firstChar && (testChar >= '0' && testChar <= '9'));
	}

	/**
 	 * Get an iterator for Tokens.
 	 * @return An token Iterator.
 	 */
	public Iterator<Token> iterator() {
		return new TokenIterator(this);
	}

	private static class TokenIterator implements Iterator<Token> {
		/* Scanner to use. */
		private Scanner scanner;

		/* Look ahead token. */
		private Token cache;

		/**
		 * Construct iterator with scanner.
		 * @param scanner The scanner to use.
		 */
		public TokenIterator(Scanner scanner) {
			this.scanner = scanner;
		}

		/**
		 * Are there more tokens?
		 */
		public boolean hasNext() {
			if (cache == null) {
				cache = scanner.next();
			}
			return !cache.isKind(Token.Kind.EOF);
		}

		/**
		 * Get the next token.
		 */
		public Token next() {
			Token token = null;
			if (cache != null) {
				token = cache;
				cache = null;
			} else {
				token = scanner.next();
			}
			return token;	
		}

		/**
		 * We don't support removing.
		 */
		public void remove() {
			//throw new OperationNotSupportedException();
			;
		}


	}

	// OPTIONAL: any other methods that you find convenient for implementation or testing
}
