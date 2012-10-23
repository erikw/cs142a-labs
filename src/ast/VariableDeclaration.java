package ast;

import crux.Symbol;

/**
 * A command for a variable declaration.
 */
public class VariableDeclaration extends Command implements Declaration, Statement {

	private Symbol sym;
	
	public VariableDeclaration(int lineNum, int charPos, Symbol sym) {
		super(lineNum, charPos);
		this.sym = sym;
	}
	
	public Symbol symbol() {
		return sym;
	}
	
	public String toString() {
		return super.toString() + "[" + sym.toString() + "]";
	}

	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
