package ast;

import crux.Symbol;

public class Call extends Command implements Statement, Expression {
	
	private Symbol func;
	private ExpressionList args;

	public Call(int lineNum, int charPos, Symbol sym, ExpressionList args) {
		super(lineNum, charPos);
		this.func = sym;
		this.args = args;
	}
	
	public Symbol function()
	{
		return func;
	}
	
	public ExpressionList arguments()
	{
		return args;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "[" + func + "]";
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}

}
