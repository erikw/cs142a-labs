package ast;

import crux.Symbol;

public class ArrayDeclaration extends Command implements Declaration, Statement {
	
	private Symbol symbol;
	
	public ArrayDeclaration(int lineNum, int charPos, Symbol symbol)
	{
		super(lineNum, charPos);
		this.symbol = symbol;
	}

	@Override
	public Symbol symbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[" + symbol.toString() + "]";
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}

}
