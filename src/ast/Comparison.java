package ast;

public class Comparison extends Command implements Expression {
	
	private Expression left;
	private Expression right;
	private Operation op;
	
	public enum Operation
	{
		GT,
		GE,
		EQ,
		NE,
		LE,
		LT;
	}

	public Comparison(int lineNum, int charPos, Expression leftSide, Operation op, Expression rightSide) {
		super(lineNum, charPos);
		left = leftSide;
		right = rightSide;
		this.op = op;
	}
	
	public Expression leftSide()
	{
		return left;
	}
	
	public Expression rightSide()
	{
		return right;
	}
	
	public Operation operation()
	{
		return op;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "[" + op + "]";
	}
	
	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
