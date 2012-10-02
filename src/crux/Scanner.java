package crux;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * A scanner that reads crux source text and produces as stream of tokens.
 */
public class Scanner implements Iterable<Token> {
    /* Author details. */
    public static String studentName = "Erik Westrup";
    public static String studentID = "50471668";
    public static String uciNetID = "ewestrup";
	
	/* Current line count. */
	private int lineNum;
	
	/* Character offset for current line. */
	private int charPos;

	/* Contains the next char (-1 == EOF). */
	// TODO constant for EOF?
	private int nextChar;

	/* TODO Input reader. */
	private Reader input;
	
	/* Construct a scanner from a reader. */
	Scanner(Reader reader)
	{
		// TODO initialize the Scanner
	}	
	
	// OPTIONAL: helper function for reading a single char from input
	//           can be used to catch and handle any IOExceptions,
	//           advance the charPos or lineNum, etc.
	/*
	private int readChar() {
	
	}
	*/
		

	/**
	 * Get the next available Token.
	 * @return Token The next Token or EOF-token if input stream contains to nore tokens.
	 *  pre: call assumes that nextChar is already holding an unread character
	 *  post: return leaves nextChar containing an untokenized character
	 */
	public Token next()
	{
		// TODO implement this
		return null;
	}

	// OPTIONAL: any other methods that you find convenient for implementation or testing
}
