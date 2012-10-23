package ast;

import crux.Symbol;

public class VariableDeclaration extends Command implements Declaration, Statement {

	private Symbol sym;
	
	public VariableDeclaration(int lineNum, int charPos, Symbol sym) {
		super(lineNum, charPos);
		this.sym = sym;
	}
	
	@Override
	public Symbol symbol() {
		return sym;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[" + sym.toString() + "]";
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}

}
