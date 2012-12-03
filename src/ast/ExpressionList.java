package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Command for an expression list.
 */
public class ExpressionList extends Command implements Iterable<Expression> {
	
	private List<Expression> list;
	
	public ExpressionList(int lineNum, int charPos) {
		super(lineNum, charPos);
		list = new ArrayList<Expression>();
	}
	
	public void add(Expression command) {
		list.add(command);
	}
	
	public int size() {
		return list.size();
	}

	public List<Expression> list() {
		return list;
	}

	public Iterator<Expression> iterator() {
		return list.iterator();
	}

	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
