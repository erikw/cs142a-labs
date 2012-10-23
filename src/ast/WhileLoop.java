package ast;

public class WhileLoop extends Command implements Statement {
	
	private Expression cond;
	private StatementList body;

	public WhileLoop(int lineNum, int charPos, Expression cond, StatementList body) {
		super(lineNum, charPos);
		this.cond = cond;
		this.body = body;
	}
	
	public Expression condition()
	{
		return cond;
	}
	
	public StatementList body()
	{
		return body;
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}

}
