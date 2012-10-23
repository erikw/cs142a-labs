package ast;

public interface CommandVisitor {
	
	public void visit(ExpressionList node);
	public void visit(DeclarationList node);
	public void visit(StatementList node);
	
	public void visit(AddressOf node);
	public void visit(LiteralBool node);
	public void visit(LiteralFloat node);
	public void visit(LiteralInt node);

	public void visit(VariableDeclaration node);
	public void visit(ArrayDeclaration node);
	public void visit(FunctionDefinition node);
	
	public void visit(Addition node);
	public void visit(Subtraction node);
	public void visit(Multiplication node);
	public void visit(Division node);
	
	public void visit(LogicalAnd node);
	public void visit(LogicalOr node);
	public void visit(LogicalNot node);
	
	public void visit(Comparison node);
	
	public void visit(Dereference node);
	public void visit(Index node);
	public void visit(Assignment node);
	public void visit(Call node);
	
	public void visit(IfElseBranch node);
	public void visit(WhileLoop node);
	public void visit(Return node);
	
	public void visit(Error node);
}
