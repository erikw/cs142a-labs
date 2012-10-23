package ast;

import crux.Token;

/**
 * An abstract command representing some action that is taken in the program.
 */
public abstract class Command implements Visitable {

	/* The line number for this command. */
	private int lineNum;

	/* The character position for this command. */
	private int charPos;

	/**
	 * Create a command on a line number and char position.
	 * @param lineNum The line number.
	 * @param charPos The character position.
	 */
	public Command(int lineNum, int charPos) {
		this.lineNum = lineNum;
		this.charPos = charPos;
	}

	/**
	 * Get the line number.
	 * @return The line number.
	 */
	public int lineNumber() {
		return lineNum;
	}

	/**
	 * Get the character positon.
	 * @return The character positon.
	 */
	public int charPosition() {
		return charPos;
	}

	/**
	 * Get a string representation of this command.
	 * @return A string representation.
	 */
	public String toString() {
		return this.getClass().getName() + "(" + lineNumber() + "," + charPosition() + ")";
	}

	/**
	 * Construct a new expression witn a LHS, RHS and an operator.
	 * @param leftSide The left side of the operator.
	 * @param op The operator to use.
	 * @param rightSide The right side of the operator.
	 * @return The constructed expression.
	 */
	public static Expression newExpression(Expression leftSide, Token op, Expression rightSide) {
		int lineNum = op.lineNumber();
		int charPos = op.charPosition();

		switch(op.kind()) {
			case ADD: return new Addition(lineNum, charPos, leftSide, rightSide);
			case SUB: return new Subtraction(lineNum, charPos, leftSide, rightSide);
			case MUL: return new Multiplication(lineNum, charPos, leftSide, rightSide);
			case DIV: return new Division(lineNum, charPos, leftSide, rightSide);

			case AND: return new LogicalAnd(lineNum, charPos, leftSide, rightSide);
			case OR:  return new LogicalOr(lineNum, charPos, leftSide, rightSide);
			case NOT: return new LogicalNot(lineNum, charPos, leftSide);

			case LESS_THAN:		return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.LT, rightSide);
			case LESSER_EQUAL:	return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.LE, rightSide);
			case EQUAL:			return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.EQ, rightSide);
			case NOT_EQUAL:		return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.NE, rightSide);
			case GREATER_EQUAL: return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.GE, rightSide);
			case GREATER_THAN:	return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.GT, rightSide);

			default: return new Error(op.lineNumber(), op.charPosition(), "Unknown Operation: " + op);
		}
	}

	/**
	 * Construct a new literal from a token.
	 * @param tok The token to use.
	 * @return The constructed expression.
	 */
	public static Expression newLiteral(Token tok) {
		switch(tok.kind()) {
			case TRUE:		return new LiteralBool(tok.lineNumber(), tok.charPosition(), LiteralBool.Value.TRUE);
			case FALSE:		return new LiteralBool(tok.lineNumber(), tok.charPosition(), LiteralBool.Value.FALSE);
			case INTEGER:	return new LiteralInt(tok.lineNumber(), tok.charPosition(), Integer.valueOf(tok.lexeme()));
			case FLOAT:		return new LiteralFloat(tok.lineNumber(), tok.charPosition(), Float.valueOf(tok.lexeme()));
			default:		return new Error(tok.lineNumber(), tok.charPosition(), "Unknown Operation: " + tok);
		}
	}
}
