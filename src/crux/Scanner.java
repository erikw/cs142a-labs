package crux;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

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
			if (nextChar == -1) {
				nextChar = EOF;
			} else if (nextChar == '\n') {
				++lineNum;
				charPos = 1; // TODO where are we now?
			} else if (nextChar != EOF) {
				++charPos;
			}
		}
		//System.out.println("next char is \"" + (char) nextChar  + "\"");
	}


	/**
	 * The states we can be in when parsing.
	 */
	private static enum State {
		BEGINNING, IDENTIFIER, DIGIT, LESS_THAN, GREATER_THAN, ASSIGN, BANG, COLON;
	}

	/**
	 * Get the next available Token.
	 * @return Token The next Token or EOF-token if input stream contains to more tokens.
	 *  pre: call assumes that nextChar is already holding an unread character (if this is not the first call)
	 *  post: return leaves nextChar containing an untokenized character.
	 */
	// TODO make sure pre: and post: holds.
	public Token next() {
		Token token = null;
		StringBuilder lexemeBuilder = new StringBuilder();
		State state = State.BEGINNING;
		if (nextChar == BOF) {
			// Don't want to block in constructor.
			readChar();
		}
		int tokBegLineNum = 0;
		int tokBegCharPos = 0;
		boolean firstLoop = true;
		while (token == null) {
			//System.out.println("loop");
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
				case BEGINNING: // TODO break out this to priv method
				 	if (Character.isWhitespace(nextChar)) {
						 //System.out.println("skipping ws");
						continue;
					}

					tokBegLineNum = lineNum;
					tokBegCharPos = charPos;
					lexemeBuilder.append(nextChar);
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
					} else {
						//System.out.println("Non-ws: \"" + (char) nextChar + "\"");
						boolean found = false;
						Collection<Token.Kind> operatorKinds = Token.Kind.getCategory(Token.Category.OPERATOR);
						Iterator<Token.Kind> iterator = operatorKinds.iterator();
						Token.Kind curKind = Token.Kind.EOF;
						while (!found && iterator.hasNext()) {
							curKind = iterator.next();
							if (curKind.matches(lexemeBuilder.toString())) {
								found = true;
							}
						}
						if (found) {
							token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, curKind);
							// Character.isLetter(nextChar) // Will allow non ASCII chars.
						} else if (matchesIdentifier(true, nextChar)) {
							state = State.IDENTIFIER;
						} else if (nextChar >= '0' && nextChar <= '9') {
							state = State.DIGIT;
						} else {
							token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.ERROR);
						}
					}
					break;
				case IDENTIFIER:
					if (matchesIdentifier(false, nextChar)) {
						lexemeBuilder.append(nextChar);
					} else {
						unreadChar();
						String identifier = lexemeBuilder.toString();
						token = Token.makeIdentifier(tokBegLineNum, tokBegCharPos, identifier);
					}
					break;
				case DIGIT:
					break;
				case LESS_THAN:
					if (nextChar == '=') {
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.LESSER_EQUAL);
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.LESS_THAN);
					}
					break;
				case GREATER_THAN:
					if (nextChar == '=') {
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.GREATER_EQUAL);
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.GREATER_THAN);
					}
					break;
				case ASSIGN:
					if (nextChar == '=') {
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.EQUAL);
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.ASSIGN);
					}
					break;
				case BANG:
					if (nextChar == '=') {
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.NOT_EQUAL);
					} else {
						unreadChar();
						token = Token.makeError(tokBegLineNum, tokBegCharPos, (char) nextChar);
					}
					break;
				case COLON:
					if (nextChar == ':') {
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.CALL);
					} else {
						unreadChar();
						token = Token.makeTokenFromKind(tokBegLineNum, tokBegCharPos, Token.Kind.COLON);
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
		// TODO construct iterato that just uses Scanner.next?
		return null;
	}

	// OPTIONAL: any other methods that you find convenient for implementation or testing
}
