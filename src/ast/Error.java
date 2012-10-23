package ast;

import crux.Symbol;

public class Error extends Command implements Declaration, Statement, Expression {
	
	private String message;

	public Error(int lineNum, int charPos, String message) {
		super(lineNum, charPos);
		this.message = message;
	}
	
	public String message()
	{
		return message;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "[" + message + "]";
	}
	
	@Override
	public Symbol symbol() {
		return Symbol.newError(message);
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}

}
