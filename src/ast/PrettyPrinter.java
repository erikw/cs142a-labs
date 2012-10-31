package ast;

/**
 * A command visitor that prints out the AST to a pretty string.
 */
public class PrettyPrinter implements CommandVisitor {
	/* The current depth. */
	private int depth = 0;

	/* the buffer to build the string in. */
	private StringBuffer sb = new StringBuffer();
	
	/**
	 * Print an object with correct indentation.
	 * @param obj The object to print.
	 */
	private void println(Object obj) {
		String indent = new String();
		for (int i = 0; i < depth; ++i) {
			indent += "  ";
		}
		sb.append(indent + obj.toString() + "\n");
	}
	
	public String toString() {
		return sb.toString();
	}
	
	public void visit(ExpressionList node) {
		println(node);
		depth++;
		for (Expression e : node) {
			e.accept(this);
		}
		depth--;
	}

	public void visit(DeclarationList node) {
		println(node);
		depth++;
		for (Declaration d : node) {
			d.accept(this);
		}
		depth--;
	}

	public void visit(StatementList node) {
		println(node);
		depth++;
		for (Statement s : node)
			s.accept(this);
		depth--;
	}

	public void visit(AddressOf node) {
		println(node);
	}

	public void visit(LiteralBool node) {
		println(node);
	}

	public void visit(LiteralFloat node) {
		println(node);
	}

	public void visit(LiteralInt node) {
		println(node);
	}

	public void visit(VariableDeclaration node) {
		println(node);
	}

	public void visit(ArrayDeclaration node) {
		println(node);
	}

	public void visit(FunctionDefinition node) {
		println(node);
		depth++;
		node.body().accept(this);
		depth--;
	}

	public void visit(Comparison node) {
	 	println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	public void visit(Addition node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	public void visit(Subtraction node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	public void visit(Multiplication node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}
	
	public void visit(Division node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	public void visit(LogicalAnd node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	public void visit(LogicalOr node) {
		println(node);
		depth++;
		node.leftSide().accept(this);
		node.rightSide().accept(this);
		depth--;
	}

	public void visit(LogicalNot node) {
		println(node);
		depth++;
		node.expression().accept(this);
		depth--;
	}
	
	public void visit(Dereference node) {
		println(node);
		depth++;
		node.expression().accept(this);
		depth--;
	}

	public void visit(Index node) {
		println(node);
		depth++;
		node.base().accept(this);
		node.amount().accept(this);
		depth--;
	}

	public void visit(Assignment node) {
		println(node);
		depth++;
		node.destination().accept(this);
		node.source().accept(this);
		depth--;
	}

	public void visit(Call node) {
		println(node);
		depth++;
		node.arguments().accept(this);
		depth--;
	}

	public void visit(IfElseBranch node) {
		println(node);
		depth++;
		node.condition().accept(this);
		node.thenBlock().accept(this);
		node.elseBlock().accept(this);
		depth--;
	}

	public void visit(WhileLoop node) {
		println(node);
		depth++;
		node.condition().accept(this);
		node.body().accept(this);
		depth--;
	}

	public void visit(Return node) {
		println(node);
		depth++;
		node.argument().accept(this);
		depth--;
	}

	public void visit(Error node) {
		println(node);
	}
}
