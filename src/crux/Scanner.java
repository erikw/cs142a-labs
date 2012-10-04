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
	private int lineNum;

	/* Character offset for current line. */
	private int charPos;

	/* Contains the next char (-1 == EOF). */
	private int nextChar;

	/* Input source. */
	private Reader input;

	/* Pushed backed character to the stream. */
	private int pushbackChar = NO_UNREAD_CHAR;

	/**
	 * Construct a scanner from a reader.
	 * @param reader The reader to use a input source.
	 */
	Scanner(Reader reader) {
		input = reader;
		lineNum = 0;
		charPos = 0;
		nextChar = BOF;
	}

	private void unreadChar(int unchar) {
		pushbackChar = unchar;
	}

	/* Re-read a previously pushed back charachter. */
	private int reunreadChar() {
		int chr = pushbackChar;
		pushbackChar = NO_UNREAD_CHAR;
		return chr;
	}

	// OPTIONAL: helper function for reading a single char from input
	//           can be used to catch and handle any IOExceptions,
	//           advance the charPos or lineNum, etc.
	private int readChar() {	
		int nextChar = 0;
		if (pushbackChar != NO_UNREAD_CHAR) {
			nextChar = reunreadChar();
		} else {
			try {
				nextChar = input.read();
			} catch (IOException ioe) {
				System.err.println("Caught IOException in input stream.");
				System.exit(1);
			}
			if (nextChar == -1) {
				nextChar = EOF;
			}
			else if (nextChar == '\n') {
				++lineNum;
				charPos = 0;
			} else if (nextChar != EOF) {
				++charPos;
			}
		}
		return nextChar;
	}


	/**
	 * Get the next available Token.
	 * @return Token The next Token or EOF-token if input stream contains to nore tokens.
	 *  pre: call assumes that nextChar is already holding an unread character
	 *  post: return leaves nextChar containing an untokenized character.
	 */
	public Token next() {
		Token token = null;
		StringBuilder lexemeBuilder = new StringBuilder();
		while (token == null) {
			int curChar = readChar();
			if (curChar == EOF) {
				token = Token.EOF(lineNum, charPos);
				continue;
			} else if (curChar == ' ' || curChar == '\t' || curChar == '\n' || curChar == '\f') {
				continue;
			} 
			
			lexemeBuilder.append(curChar);
			if (Token.Kind.LESS_THAN.matches(lexemeBuilder.toString())) {

			} else {
				System.out.println("Non-ws: \"" + (char) curChar + "\"");
				boolean found = false;
				Collection<Token.Kind> operatorKinds = Token.Kind.getCategory(Token.Category.OPERATOR);
				Iterator<Token.Kind> iterator = operatorKinds.iterator();
				Token.Kind curKind;
				while (!found && iterator.hasNext()){
					curKind = iterator.next();
					if (curKind.matches(lexemeBuilder.toString())){
						found = true;
					}
				}
				if (found) {
					return Token.makeOperator(curKind, lineNum, charPos);
				}

			}
		}

		return token;
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
