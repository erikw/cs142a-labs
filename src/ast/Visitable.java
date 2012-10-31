package ast;

/**
 * Interface for the Visitor pattern.
 *
 * Implementating classes can be visited by a vistor.
 */
public interface Visitable {

	/**
	 * Accept a visitor on this object.
	 */
	public void accept(CommandVisitor visitor);
}
