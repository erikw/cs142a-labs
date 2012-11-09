package types;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import crux.Symbol;
import ast.*;

/**
 * A visitor that performed type checking on an AST.
 */
public class TypeChecker implements CommandVisitor {
    /* A map of types TODO */
    private Map<Command, Type> typeMap;

    /* Buffered error messages. */
    private StringBuilder errorBuffer;

    // TODO am I suppose to implement these some where?
    /* Useful error strings:
     *
     * "Function " + func.name() + " has a void argument in position " + pos + "."
     * "Function " + func.name() + " has an error in argument in position " + pos + ": " + error.getMessage()
     *
     * "Function main has invalid signature."
     *
     * "Not all paths in function " + currentFunctionName + " have a return."
     *
     * "IfElseBranch requires bool condition not " + condType + "."
     * "WhileLoop requires bool condition not " + condType + "."
     *
     * "Function " + currentFunctionName + " returns " + currentReturnType + " not " + retType + "."
     *
     * "Variable " + varName + " has invalid type " + varType + "."
     * "Array " + arrayName + " has invalid base type " + baseType + "."
     */

    /**
     * Create a new type checker.
     */
    public TypeChecker() {
        typeMap = new HashMap<Command, Type>();
        errorBuffer = new StringBuilder();
    }

    /**
     * Report an error.
     * @param lineNum The line number the error occured on.
     * @param charPos The character position the error occured on.
     * @param message A describing of the error.
     */
    private void reportError(int lineNum, int charPos, String message) {
        errorBuffer.append("TypeError(" + lineNum + "," + charPos + ")");
        errorBuffer.append("[" + message + "]" + "\n");
    }

	/**
	 * Put a command with its type in the type map.
	 * @param node The command node.
	 * @param type The type for the node.
	 */
    private void put(Command node, Type type) {
        if (type instanceof ErrorType) {
            reportError(node.lineNumber(), node.charPosition(), ((ErrorType)type).getMessage());
        } else {
        	typeMap.put(node, type);
        }
    }
    
    /**
     * Get the type for a given node.
     * @param node The node to get the type for.
     * @return The found type or null.
     */
    public Type getType(Command node) {
        return typeMap.get(node);
    }
    
    /**
     * Check a given AST for type errors.
     * @param ast The AST to check.
     * @return Indication of success or failre.
     */
    public boolean check(Command ast) {
        ast.accept(this);
        return !hasError();
    }
    
    /**
     * Query for found errors.
     * @return Indication of error presence.
     */
    public boolean hasError() {
        return errorBuffer.length() != 0;
    }
    
    /**
     * Get the error report.
     * @return The error report.
     */
    public String errorReport() {
        return errorBuffer.toString();
    }

    /**
     * Visit and get type for node.
     * @param node The node to visit.
     * @return The type of the node.
     */
    private Type visitRetriveType(Visitable node) {
		node.accept(this);
		return getType((Command) node); // TODO not good to cast...
    }

// Visitor methods ===================================

    @Override
    public void visit(ExpressionList node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(DeclarationList node) {
        for (Declaration decl : node) {
        	decl.accept(this);
        }
    }

    @Override
    public void visit(StatementList node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(AddressOf node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LiteralBool node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LiteralFloat node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LiteralInt node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(VariableDeclaration node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(ArrayDeclaration node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(FunctionDefinition node) {
        Symbol func = node.function();
        List<Symbol> args = node.arguments();
        Type returnType = ((FuncType) func.type()).returnType();
        if (func.name().equals("main") && (args.size() !=0 || !(returnType instanceof VoidType))) {
			put(node, new ErrorType("Function main has invalid signature."));
			return;
        }
        int pos = 0;
        for (Symbol arg : args) {
			Type argType = arg.type();
			if (argType instanceof ErrorType) {
				put(node, new ErrorType("Function " + func.name() + " has an error in argument in position " + pos + ": " + ((ErrorType) argType).getMessage()));
				return;
			} else if (argType instanceof VoidType) {
 	 	 	 	put(node, new ErrorType("Function " + func.name() + " has a void argument in position " + pos + "."));
				return;
			}
			++pos;
        }
        put(node, returnType);
    }

    @Override
    public void visit(Comparison node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Addition node) {
        Type lhs = visitRetriveType(node.leftSide());
        Type rhs = visitRetriveType(node.rightSide());
		put(node, lhs.add(rhs));
    }

    @Override
    public void visit(Subtraction node) {
        Type lhs = visitRetriveType(node.leftSide());
        Type rhs = visitRetriveType(node.rightSide());
		put(node, lhs.sub(rhs));
    }

    @Override
    public void visit(Multiplication node) {
        Type lhs = visitRetriveType(node.leftSide());
        Type rhs = visitRetriveType(node.rightSide());
		put(node, lhs.mul(rhs));
    }

    @Override
    public void visit(Division node) {
        Type lhs = visitRetriveType(node.leftSide());
        Type rhs = visitRetriveType(node.rightSide());
		put(node, lhs.div(rhs));
    }

    @Override
    public void visit(LogicalAnd node) {
        Type lhs = visitRetriveType(node.leftSide());
        Type rhs = visitRetriveType(node.rightSide());
		put(node, lhs.and(rhs));
    }

    @Override
    public void visit(LogicalOr node) {
        Type lhs = visitRetriveType(node.leftSide());
        Type rhs = visitRetriveType(node.rightSide());
		put(node, lhs.or(rhs));
    }

    @Override
    public void visit(LogicalNot node) {
		put(node, visitRetriveType(node.expression()));
    }

    @Override
    public void visit(Dereference node) {
        put(node, visitRetriveType(node.expression()));
    }

    @Override
    public void visit(Index node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Assignment node) {
        Type srcType = visitRetriveType(node.source());
        Type destType = visitRetriveType(node.destination());
        put(node, destType.assign(srcType));
    }

    @Override
    public void visit(Call node) {
    	Symbol func = node.function();
    	FuncType funcType = (FuncType) func.type(); // TODO sigh so ugly, right?
    	ExpressionList args = node.arguments();
    	Type argTypes = visitRetriveType(args);
		put(node, funcType.call(argTypes));
    }

    @Override
    public void visit(IfElseBranch node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(WhileLoop node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Return node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(ast.Error node) {
        put(node, new ErrorType(node.message()));
    }
}
