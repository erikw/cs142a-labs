package ast;

import crux.Symbol;

/**
 * Command for different kinds of errors.
 */
public class Error extends Command implements Declaration, Statement, Expression {

	private String message;

	public Error(int lineNum, int charPos, String message) {
		super(lineNum, charPos);
		this.message = message;
	}

	public String message() {
		return message;
	}

	public String toString() {
		return super.toString() + "[" + message + "]";
	}

	public Symbol symbol() {
		return Symbol.newError(message);
	}

	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
