package ast;

/**
 * Command for a dereference.
 */
public class Dereference extends Command implements Expression {
	private Expression expression;
	
	public Dereference(int lineNum, int charPos, Expression expression) {
		super(lineNum, charPos);
		this.expression = expression;
	}
	
	public Expression expression() {
		return expression;
	}

	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
