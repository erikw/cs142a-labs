package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExpressionList extends Command implements Iterable<Expression> {
	
	private List<Expression> list;
	
	public ExpressionList(int lineNum, int charPos)
	{
		super(lineNum, charPos);
		list = new ArrayList<Expression>();
	}
	
	public void add(Expression command)
	{
		list.add(command);
	}
	
	public int size()
	{
		return list.size();
	}

	@Override
	public Iterator<Expression> iterator() {
		return list.iterator();
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visit(this);
	}
}
