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
	public static final boolean DEBUG = true;

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

	/**
	 * Generate a unique name in the crux namespace for functions to not conflict with MIPS built ins.
	 * @param name The input name.
	 * @return A mangled version of input.
	 */
    private String newFunLabel(String name) {
        return "cruxfunc." + name;
    }

	/**
	 * Generate a unique name in the crux namespace for functions to not conflict with MIPS built ins.
	 * @param name The input name.
	 * @return A mangled version of input.
	 */
    private String namespaceFunc(String name) {
        return "cruxfunc." + name;
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
        program.debugComment("Taking address of variable.");
		currentFunction.getAddress(program, "$t0" , node.symbol());
		program.pushInt("$t0");
    }

    @Override
    public void visit(LiteralBool node) {
        int intVal = -1;
        switch (node.value()) {
		case TRUE:
			  intVal = 1;
			  break;
		case FALSE:
			  intVal = 0;
        }
		//program.appendInstruction("addi $t0, $0, " + intVal);
		program.appendInstruction("li $t0, " + intVal);
		program.pushInt("$t0");
    }

    @Override
    public void visit(LiteralFloat node) {
    	Float value = node.value();
		program.appendInstruction("li.s $f0, " + value);
		program.pushFloat("$f0");
    }

    @Override
    public void visit(LiteralInt node) {
    	int value = node.value();
		//program.appendInstruction("addi $t0, $0, " + value);
		program.appendInstruction("li $t0, " + value);
		program.pushInt("$t0");
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
		String funcName = node.symbol().name();
		boolean isMain = funcName.equals("main");
        currentFunction = new ActivationRecord(node, currentFunction);
		program.debugComment("Function definition  starts here.");
		if (!isMain) {
			funcName = namespaceFunc(node.function().name());
		}
		int startPos = program.appendInstruction(funcName + ":");
		program.debugComment("Function body begins here.");
		node.body().accept(this);
		program.insertPrologue((startPos + 1), currentFunction.stackSize(), isMain);

		// TODO Callee Epilogue
		program.appendEpilogue(currentFunction.stackSize(), isMain);


		Type retType  = ((FuncType) node.function().type()).returnType();
		if (!retType.equivalent(new VoidType())) {
			program.debugComment("Saving return value.");
			program.appendInstruction("lw $v0, 0($sp)");
		}

    	currentFunction = currentFunction.parent();
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
        program.debugComment("Dereferencing address.");
        node.expression().accept(this);
        program.popInt("$t0"); // Contains address to type ,/
        Type type = typeChecker.getType(node); // TODO type can be array 3 of array 2 of floatboat
        if (type.equivalent(new FloatType())) {
			program.appendInstruction("lwc1 $f0, 0($t0)");
			program.pushFloat("$f0");
        } else if (type.equivalent(new IntType()) || type.equivalent(new BoolType())) {
			program.appendInstruction("lw $t1, 0($t0)");
			program.pushInt("$t1");
        }
    }

    @Override
    public void visit(Index node) {
        program.debugComment("Taking index of expression.");
        node.base().accept(this);
        program.debugComment("Popping base address. ");
        program.popInt("$t0"); 
        node.amount().accept(this);
        program.debugComment("Popping amount. ");
        program.popInt("$t1");

        program.debugComment("Calculate base + offset");
        Type type = typeChecker.getType(node);
		program.appendInstruction("li $t2, " + type.numBytes());
		program.appendInstruction("mul $t1, $t1, $t2");
		program.appendInstruction("add $t0, $t0, $t1");
		program.pushInt("$t0");

    }

    @Override
    public void visit(Assignment node) {
    	program.debugComment("Assignment beginns.");
    	program.debugComment("Handle destination.");
    	node.destination().accept(this);
    	program.popInt("$t0");
    	program.debugComment("Handle source.");
    	node.source().accept(this);
    	Type type = typeChecker.getType(node);
        if (type instanceof ArrayType) {
        	// TODO 
        } else if (type.equivalent(new FloatType())) { 
        	program.popFloat("$f0");
        	program.appendInstruction("swc1 $f0, 0($t0)");
        } else {
        	program.popInt("$t1");
        	program.appendInstruction("sw $t1, 0($t0)");
        }
    }

    @Override
    public void visit(Call node) {
        program.debugComment("Caller Setup");
        ExpressionList args = node.arguments();
        int argFrameSize = 0;
        if (args.size() > 0) {
        	program.debugComment("Evaluate function arguments.");
        	int frameSizeBefore = currentFunction.stackSize();
        	for (Expression expr : args) {
				expr.accept(this);
        	}
        	argFrameSize = currentFunction.stackSize() - frameSizeBefore;
        }

        String funcName =  node.function().name();
        if (!funcName.matches("print(Bool|Float|Int|ln)|read(Float|Int)")) {
        	funcName = namespaceFunc(funcName);
        } else {
        	funcName = "func." + funcName;
        }
        program.appendInstruction("jal " + funcName);

        program.debugComment("Caller Teardown.");
        if (argFrameSize > 0) {
        	program.debugComment("Cleaning up used function args.");
 			program.appendInstruction("addi $sp, $sp, " + argFrameSize);
        }

		FuncType func = (FuncType) node.function().type();
 		if (!func.returnType().equivalent(new VoidType())) {
        	program.debugComment("Pop-push'n return value.");
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
        program.debugComment("Pop-push return value.");
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
