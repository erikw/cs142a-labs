package ast;

import crux.Symbol;

/**
 * Interface for a declaration.
 */
public interface Declaration extends Visitable {
	
	/**
	 * Get the sumbol of this declaration.
	 * @return The symbol
	 */
	public Symbol symbol();
}
