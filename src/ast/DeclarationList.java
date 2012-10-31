package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Command for a declaration list.
 */
public class DeclarationList extends Command implements Iterable<Declaration> {
	
	/* The list of declarations. */
	private List<Declaration> list;
	
	public DeclarationList(int lineNum, int charPos) {
		super(lineNum, charPos);
		list = new ArrayList<Declaration>();
	}
	
	/**
	 * Add a declaration to this list.
	 * @param command The declaration to add.
	 */
	public void add(Declaration command) {
		list.add(command);
	}

	public Iterator<Declaration> iterator() {
		return list.iterator();
	}

	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
