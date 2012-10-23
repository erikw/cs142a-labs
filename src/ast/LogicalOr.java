package ast;

public class LogicalOr extends Command implements Expression {
	private Expression left;
	private Expression right;
	
	public LogicalOr(int lineNum, int charPos, Expression leftSide, Expression rightSide) {
		super(lineNum, charPos);
		left = leftSide;
		right = rightSide;
	}
	
	public Expression leftSide()
	{
		return left;
	}
	
	public Expression rightSide()
	{
		return right;
	}
	
	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}