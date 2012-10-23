package ast;

import crux.Token;

public abstract class Command implements Visitable {
	
	private int lineNum;
	private int charPos;
	
	public Command(int lineNum, int charPos)
	{
		this.lineNum = lineNum;
		this.charPos = charPos;
	}
	
	public int lineNumber()
	{
		return lineNum;
	}
	
	public int charPosition()
	{
		return charPos;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName() + "(" + lineNumber() + "," + charPosition() + ")";
	}

	public static Expression newExpression(Expression leftSide, Token op, Expression rightSide)
	{
		int lineNum = op.lineNumber();
		int charPos = op.charPosition();
		
		switch(op.kind())
		{
		case ADD: return new Addition(lineNum, charPos, leftSide, rightSide);
		case SUB: return new Subtraction(lineNum, charPos, leftSide, rightSide);
		case MUL: return new Multiplication(lineNum, charPos, leftSide, rightSide);
		case DIV: return new Division(lineNum, charPos, leftSide, rightSide);
		
		case AND: return new LogicalAnd(lineNum, charPos, leftSide, rightSide);
		case OR:  return new LogicalOr(lineNum, charPos, leftSide, rightSide);
		case NOT: return new LogicalNot(lineNum, charPos, leftSide);
		
		case LESS_THAN:     return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.LT, rightSide);
		case LESSER_EQUAL:  return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.LE, rightSide);
		case EQUAL:         return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.EQ, rightSide);
		case NOT_EQUAL:     return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.NE, rightSide);
		case GREATER_EQUAL: return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.GE, rightSide);
		case GREATER_THAN:  return new Comparison(lineNum, charPos, leftSide, Comparison.Operation.GT, rightSide);
		
		default: return new Error(op.lineNumber(), op.charPosition(), "Unknown Operation: " + op);
		}
	}
	
	public static Expression newLiteral(Token tok)
	{
		switch(tok.kind())
		{
		case TRUE: return new LiteralBool(tok.lineNumber(), tok.charPosition(), LiteralBool.Value.TRUE);
		case FALSE: return new LiteralBool(tok.lineNumber(), tok.charPosition(), LiteralBool.Value.FALSE);
		case INTEGER: return new LiteralInt(tok.lineNumber(), tok.charPosition(), Integer.valueOf(tok.lexeme()));
		case FLOAT: return new LiteralFloat(tok.lineNumber(), tok.charPosition(), Float.valueOf(tok.lexeme()));
		default: return new Error(tok.lineNumber(), tok.charPosition(), "Unknown Operation: " + tok);
		}
	}
}
