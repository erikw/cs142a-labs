package ast;

public class LiteralInt extends Command implements Expression {
	
	private Integer value;

	public LiteralInt(int lineNum, int charPos, Integer value) {
		super(lineNum, charPos);
		this.value = value;
	}
	
	public Integer value()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "[" + value + "]";
	}
	
	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
	
}
