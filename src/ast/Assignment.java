package ast;

public class Assignment extends Command implements Statement {
	
	private Expression dest;
	private Expression source;

	public Assignment(int lineNum, int charPos, Expression dest, Expression source) {
		super(lineNum, charPos);
		this.dest = dest;
		this.source = source;
	}
	
	public Expression destination()
	{
		return dest;
	}
	
	public Expression source()
	{
		return source;
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}

}
