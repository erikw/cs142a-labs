package ast;

public class Dereference extends Command implements Expression {
	
	private Expression expression;
	
	public Dereference(int lineNum, int charPos, Expression expression)
	{
		super(lineNum, charPos);
		this.expression = expression;
	}
	
	public Expression expression()
	{
		return expression;
	}

	@Override
	public void accept(CommandVisitor visitor)
	{
		visitor.visit(this);
	}
}
