package ast;

import java.util.List;

import crux.Symbol;

public class FunctionDefinition extends Command implements Declaration {

	private Symbol func;
	private StatementList body;
	private List<Symbol> args;
	
	public FunctionDefinition(int lineNum, int charPos, Symbol func, List<Symbol> args, StatementList body)
	{
		super(lineNum, charPos);
		this.func = func;
		this.args = args;
		this.body = body;
	}
	
	public Symbol function()
	{
		return func;
	}
	
	public StatementList body()
	{
		return body;
	}
	
	public List<Symbol> arguments()
	{
		return args;
	}

	@Override
	public Symbol symbol() {
		return func;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[" + func + ", " + args + "]";
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
