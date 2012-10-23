package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatementList extends Command implements Iterable<Statement> {
	
	private List<Statement> list;
	
	public StatementList(int lineNum, int charPos)
	{
		super(lineNum, charPos);
		list = new ArrayList<Statement>();
	}
	
	public void add(Statement command)
	{
		list.add(command);
	}

	@Override
	public Iterator<Statement> iterator() {
		return list.iterator();
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
