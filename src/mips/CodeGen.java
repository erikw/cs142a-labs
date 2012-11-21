package mips;

import java.util.regex.Pattern;

import ast.*;
import types.*;

/**
 * A visitor that generated MIPS assembly code.
 */
public class CodeGen implements ast.CommandVisitor {

    /* Collected error messages. */
    private StringBuilder errorBuffer = new StringBuilder();

    /* The type checker. */
    private TypeChecker typeChecker;

    /* The program we're building. */
    private Program program;

    /* The current activation record. */
    private ActivationRecord currentFunction;

    /**
     * Construct a new code generator using the given type checker.
     * @param TypeChecker The type checker to use.
     */
    public CodeGen(TypeChecker typeChecker) {
        this.typeChecker = typeChecker;
        this.program = new Program();
    }

    /**
     * Query for errors.
     * @return Indication of the precense of errors.
     */
    public boolean hasError() {
        return errorBuffer.length() != 0;
    }

    /**
     * Get an error report.
     * @return An error report.
     */
    public String errorReport() {
        return errorBuffer.toString();
    }

	/**
	 * Exception representing an error in the code generation.
	 */
    private class CodeGenException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /**
         * Construct an error with an message.
         * @param errorMessage The error message.
         */
        public CodeGenException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * Generate assemly program on an AST
     * @param ast The AST to generate code from.
     * @return Success indication.
     */
    public boolean generate(Command ast) {
        boolean error = false;
        try {
            currentFunction = ActivationRecord.newGlobalFrame();
            ast.accept(this);
            error = !hasError();
        } catch (CodeGenException e) {
            error = true;
        }
        return !error;
    }

    /**
     * Get the generated program representation.
     * @return The program.
     */
    public Program getProgram() {
        return program;
    }

	// Visitor methods ===================================

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
    public void visit(Comparison node) {
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
        String message = "CodeGen cannot compile a " + node;
        errorBuffer.append(message);
        throw new CodeGenException(message);
    }
}
