package ast;

public class IfElseBranch extends Command implements Statement {
	
	private Expression cond;
	private StatementList thenBlock;
	private StatementList elseBlock;

	public IfElseBranch(int lineNum, int charPos, Expression cond, StatementList thenBlock, StatementList elseBlock) {
		super(lineNum, charPos);
		this.cond = cond;
		this.thenBlock = thenBlock;
		this.elseBlock = elseBlock;
	}
	
	public Expression condition()
	{
		return cond;
	}
	
	public StatementList thenBlock()
	{
		return thenBlock;
	}
	
	public StatementList elseBlock()
	{
		return elseBlock;
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}

}
