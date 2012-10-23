package ast;

public class PrettyPrinter implements CommandVisitor {
	
	private int depth = 0;
	private StringBuffer sb = new StringBuffer();
	
	private void println(Object obj)
	{
		String indent = new String();
		for (int i = 0; i < depth; i++) {
			indent += "  ";
		}
		
		sb.append(indent + obj.toString() + "\n");
	}
	
	public String toString()
	{
		return sb.toString();
	}
	
	@Override
	public void visit(ExpressionList node) {
		println(node);
		depth++;
		for (Expression e : node)
			e.accept(this);
		depth--;
	}

	@Override
	public void visit(DeclarationList node) {
		println(node);
		depth++;
		for (Declaration d : node)
			d.accept(this);
		depth--;
	}

	@Override
	public void visit(StatementList node) {
		println(node);
		depth++;
		for (Statement s : node)
			s.accept(this);
		depth--;
	}

	@Override
	public void visit(AddressOf node) {
		println(node);
	}

	@Override
	public void visit(LiteralBool node) {
		println(node);
	}

	@Override
	public void visit(LiteralFloat node) {
		println(node);
	}

	@Override
	public void visit(LiteralInt node) {
		println(node);
	}

	@Override
	public void visit(VariableDeclaration node) {
		println(node);
	}

	@Override
	public void visit(ArrayDeclaration node) {
		println(node);
	}

	@Override
	public void visit(FunctionDefinition node) {
		println(node);
		depth++;
		node.body().accept(this);
		depth--;
	}

	@Override
	public void visit(Comparison node) {
	 	println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	@Override
	public void visit(Addition node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	@Override
	public void visit(Subtraction node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	@Override
	public void visit(Multiplication node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}
	
	@Override
	public void visit(Division node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	@Override
	public void visit(LogicalAnd node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	@Override
	public void visit(LogicalOr node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	@Override
	public void visit(LogicalNot node) {
		println(node);
		depth++;
		node.expression().accept(this);
		depth--;
	}
	
	@Override
	public void visit(Dereference node)
	{
		println(node);
		depth++;
		node.expression().accept(this);
		depth--;
	}

	@Override
	public void visit(Index node) {
		println(node);
		depth++;
		node.base().accept(this);
		node.amount().accept(this);
		depth--;
	}

	@Override
	public void visit(Assignment node) {
		println(node);
		depth++;
		node.destination().accept(this);
		node.source().accept(this);
		depth--;
	}

	@Override
	public void visit(Call node) {
		println(node);
		depth++;
		node.arguments().accept(this);
		depth--;
	}

	@Override
	public void visit(IfElseBranch node) {
		println(node);
		depth++;
		node.condition().accept(this);
		node.thenBlock().accept(this);
		node.elseBlock().accept(this);
		depth--;
	}

	@Override
	public void visit(WhileLoop node) {
		println(node);
		depth++;
		node.condition().accept(this);
		node.body().accept(this);
		depth--;
	}

	@Override
	public void visit(Return node) {
		println(node);
		depth++;
		node.argument().accept(this);
		depth--;
	}

	@Override
	public void visit(Error node) {
		println(node);
	}

}
