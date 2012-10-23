package ast;

/**
 * Command for a locigal not.
 */
public class LogicalNot extends Command implements Expression {
	private Expression expr;

	public LogicalNot(int lineNum, int charPos, Expression expr) {
		super(lineNum, charPos);
		this.expr = expr;
	}

	public Expression expression() {
		return expr;
	}

	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
