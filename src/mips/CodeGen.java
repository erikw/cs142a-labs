package mips;

import java.util.regex.Pattern;

import ast.*;
import types.*;

/**
 * A visitor that generated MIPS assembly code.
 */
public class CodeGen implements ast.CommandVisitor {

	/* Set this to true to also emitt debugging comments describint the 
	 * assemly code generated. */
	// TODO set to false before hand in.
	private static final DEBUG = true;

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
     * Generate assemly program on an AST.
     * @param ast The AST to generate code from.
     * @return Success indication.
     */
    public boolean generate(Command ast) {
        boolean error = false;
        try {
            currentFunction = ActivationRecord.newGlobalFrame();
            ast.accept(this);
            error = !hasError();
        } catch (CodeGenException cge) {
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
        for (Declaration decl : node) {
        	decl.accept(this);
        }
    }

    @Override
    public void visit(StatementList node) {
        for (Statement stmt : node) {
        	stmt.accept(this);
        }
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
		currentFunction.add(program, node);
    }

    @Override
    public void visit(ArrayDeclaration node) {
		currentFunction.add(program, node);
    }

    @Override
    public void visit(FunctionDefinition node) {
        currentFunction = new ActivationRecord(node, currentFunction);
        // TODO handle function args
	 	// TODO Callee Prologue
	 	// TODO Callee Execution
		// TODO print function label here?
		 node.body().accept(this);

		 // TODO Callee Epilogue
    	currentFunction = currentFunction.parent();
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
        program.debugComment("Caller Setup");
        program.debugComment("Evaluate function arguments.")
        ExpressionList args = node.arguments();
        for (Expression expr : args) {
			expr.accept(this);
        }
        String funcName = "crux_fun_" + node.function().name(); // TODO use program.newlabel or what is that for?
        program.appendInstruction("jal " + funcName);
       
        program.debugComment("Caller Teardown");
        // TODO Caller Teardown, pop off args
        FuncType func = (FuncThpe) node.function().type(); // TODO ugly
		int nbrArgs = func.arguments().size();
		//int nbrArgs = args.size();
        // TODO are actual args stored or pointers to them? how get size of real stored?
 		program.appendInstruction("addi $sp, $sp, " + (4 * nbrArgs))
 
 		if (!func.returnType().equivalent(new VoidType())) {
        	program.debugComment("Pop-push return value.");
			program.appendInstruction("subu $sp, $sp, 4");
			program.appendInstruction("sw $v0, 0($sp)");
 		}
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
