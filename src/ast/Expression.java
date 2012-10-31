package ast;

/**
 * Interface for expressions.
 */
public interface Expression extends Visitable {

	/**
	 * Get the line number.
	 * @return The line number.
	 */
	public int lineNumber();

	/**
	 * Get the character positon.
	 * @return The character positon.
	 */
	public int charPosition();
}
