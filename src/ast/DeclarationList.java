package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeclarationList extends Command implements Iterable<Declaration> {
	
	private List<Declaration> list;
	
	public DeclarationList(int lineNum, int charPos)
	{
		super(lineNum, charPos);
		list = new ArrayList<Declaration>();
	}
	
	public void add(Declaration command)
	{
		list.add(command);
	}

	@Override
	public Iterator<Declaration> iterator() {
		return list.iterator();
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
