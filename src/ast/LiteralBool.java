package ast;

/**
 * Command for a literal boolean type.
 */
public class LiteralBool extends Command implements Expression {
	
	/**
	 * The possible values of a boolean type.
	 */
	public enum Value
	{
		FALSE,
		TRUE;
	}

	private Value value;
	
	public LiteralBool(int lineNum, int charPos, Value value) {
		super(lineNum, charPos);
		this.value = value;
	}
	
	public Value value() {
		return value;
	}
	
	public String toString() {
		return super.toString() + "[" + value + "]";
	}
	
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
