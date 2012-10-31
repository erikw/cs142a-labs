package ast;

/**
 * Command for a literal float type.
 */
public class LiteralFloat extends Command implements Expression {
	
	private Float value;

	public LiteralFloat(int lineNum, int charPos, Float value) {
		super(lineNum, charPos);
		this.value = value;
	}
	
	public Float value() {
		return value;
	}
	
	public String toString() {
		return super.toString() + "[" + value + "]";
	}

	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
