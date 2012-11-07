package types;

import java.util.HashMap;
import ast.*;

/**
 * A visitor that performed type checking on an AST.
 */
public class TypeChecker implements CommandVisitor {
    /* A map of types TODO */
    // TODO change to Map?
    private HashMap<Command, Type> typeMap;

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
     * Report error.
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
        }
        typeMap.put(node, type);
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

    @Override
    public void visit(ExpressionList node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(DeclarationList node) {
        throw new RuntimeException("Implement this");
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
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Comparison node) {
        throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Addition node) {
        throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Subtraction node) {
        throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Multiplication node) {
        throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Division node) {
        throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(LogicalAnd node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LogicalNot node) {
        throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Dereference node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Index node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Assignment node) {
        throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Call node) {
        throw new RuntimeException("Implement this");
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
