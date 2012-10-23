package ast;

public class Index extends Command implements Expression {

	private Expression base;
	private Expression amount;
	
	public Index(int lineNum, int charPos, Expression base, Expression amount) {
		super(lineNum, charPos);
		this.base = base;
		this.amount = amount;
	}
	
	public Expression base()
	{
		return base;
	}
	
	public Expression amount()
	{
		return amount;
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
