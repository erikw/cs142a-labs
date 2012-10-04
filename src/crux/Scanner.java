package crux;

//import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * A scanner that reads crux source text and produces as stream of tokens.
 */
public class Scanner implements Iterable<Token>
{
    /* Represents Begnning of File. */
    private static final short BOF = -1;

    /* Represents End of File. */
    private static final short EOF = -1;

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

	/**
	 * Construct a scanner from a reader.
	 * @param reader The reader to use a input source.
	 */
	Scanner(Reader reader)
	{
		input = reader;
		lineNum = 0;
		charPos = 0;
		nextChar = BOF;
	}

	// OPTIONAL: helper function for reading a single char from input
	//           can be used to catch and handle any IOExceptions,
	//           advance the charPos or lineNum, etc.
	private int readChar()
	{
		return 0;
	}


	/**
	 * Get the next available Token.
	 * @return Token The next Token or EOF-token if input stream contains to nore tokens.
	 *  pre: call assumes that nextChar is already holding an unread character
	 *  post: return leaves nextChar containing an untokenized character
	 */
	public Token next()
	{
		int curChar = readChar();

		// TODO skip ws
		// TODO mkToken or set state based on first char
		// TODO switch or state try to match more.
		return null;
	}

	/**
	 * Get an iterator for Tokens.
	 * @return An token Iterator.
	 */
	public Iterator<Token> iterator()
	{
		// TODO construct iterato that just uses Scanner.next?
		return null;
	}

	// OPTIONAL: any other methods that you find convenient for implementation or testing
}
