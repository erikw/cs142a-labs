package crux;

public class Token {
	
	public static enum Kind {
		AND("and"),
		OR("or"),
		NOT("not"),
		
		ADD("+"),
		SUB("-"),
		MUL("*"),
		DIV("/"),
		
		IDENTIFIER(),
		INTEGER(),
		FLOAT(),
		ERROR(),
		EOF();
		
		// TODO: complete the list of possible tokens
		
		private String default_lexeme;
		
		Kind()
		{
			default_lexeme = "";
		}
		
		Kind(String lexeme)
		{
			default_lexeme = lexeme;
		}
		
		public boolean hasStaticLexeme()
		{
			return default_lexeme != null;
		}
		
		// OPTIONAL: if you wish to also make convenience functions, feel free
		//           for example, boolean matches(String lexeme)
		//           can report whether a Token.Kind has the given lexeme
	}
	
	private int lineNum;
	private int charPos;
	Kind kind;
	private String lexeme = "";
	
	
	// OPTIONAL: implement factory functions for some tokens, as you see fit
	/*           
	public static Token EOF(int linePos, int charPos)
	{
		Token tok = new Token(linePos, charPos);
		tok.kind = Kind.EOF;
		return tok;
	}
	*/

	private Token(int lineNum, int charPos)
	{
		this.lineNum = lineNum;
		this.charPos = charPos;
		
		// if we don't match anything, signal error
		this.kind = Kind.ERROR;
		this.lexeme = "No Lexeme Given";
	}
	
	public Token(String lexeme, int lineNum, int charPos)
	{
		this.lineNum = lineNum;
		this.charPos = charPos;
		
		// TODO: based on the given lexeme determine and set the actual kind
		
		// if we don't match anything, signal error
		this.kind = Kind.ERROR;
		this.lexeme = "Unrecognized lexeme: " + lexeme;
	}
	
	public int lineNumber()
	{
		return lineNum;
	}
	
	public int charPosition()
	{
		return charPos;
	}
	
	// Return the lexeme representing or held by this token
	public String lexeme()
	{
		// TODO: implement
		return null;
	}
	
	public String toString()
	{
		// TODO: implement this
		return "Not Yet Implemented";
	}
	
	// OPTIONAL: function to query a token about its kind
	//           boolean is(Token.Kind kind)
	
	// OPTIONAL: add any additional helper or convenience methods
	//           that you find make for a clean design

}
