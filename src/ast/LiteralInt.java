package ast;

/**
 * Command for a literal interger type.
 */
public class LiteralInt extends Command implements Expression {
	
	private Integer value;

	public LiteralInt(int lineNum, int charPos, Integer value) {
		super(lineNum, charPos);
		this.value = value;
	}
	
	public Integer value() {
		return value;
	}
	
	public String toString() {
		return super.toString() + "[" + value + "]";
	}
	
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
