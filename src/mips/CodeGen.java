package mips;

import java.util.ArrayList;
import java.util.ListIterator;

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
        program.debugComment("Taking address of variable " + node.symbol().name() + ".");
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
        program.debugComment("Literalbool == " + intVal);
		program.appendInstruction("li $t0, " + intVal);
		program.pushInt("$t0");
    }

    @Override
    public void visit(LiteralFloat node) {
    	Float value = node.value();
        program.debugComment("LiteralFloat == " + value);
		program.appendInstruction("li.s $f0, " + value);
		program.pushFloat("$f0");
    }

    @Override
    public void visit(LiteralInt node) {
    	int value = node.value();
        program.debugComment("LiteralInt == " + value);
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
		program.debugComment("Register argument symbols.");
        currentFunction.add(program, node);

		program.debugComment("Function body begins here.");
		node.body().accept(this);
		program.insertPrologue((startPos + 1), currentFunction.stackSize(), isMain);



		Type retType  = ((FuncType) node.function().type()).returnType();
		if (!retType.equivalent(new VoidType())) {
			program.debugComment("Storing return value.");
			if (retType.equivalent(new FloatType())) {
				program.popFloat("$v0");
			} else if (retType.equivalent(new IntType()) || retType.equivalent(new BoolType())) {
				program.popInt("$v0");
			}
		}

		program.appendEpilogue(currentFunction.stackSize(), isMain);

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
        program.debugComment("Dereferencing address. Now getting address.");
        node.expression().accept(this);
        program.popInt("$t0"); // Contains address to type ,/
        Type type = typeChecker.getType(node); // TODO type can be array 3 of array 2 of floatboat
        program.debugComment("Load value at the found address.");
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
        node.amount().accept(this);
        program.debugComment("Popping amount. ");
        program.popInt("$t0");
        program.debugComment("Popping base address.");
        program.popInt("$t1"); 

        program.debugComment("Calculate base + offset");
        Type type = typeChecker.getType(node);
		program.appendInstruction("li $t2, " + type.numBytes());
		program.appendInstruction("mul $t0, $t0, $t2");
		program.appendInstruction("add $t1, $t1, $t0");
		program.pushInt("$t1");

    }

    @Override
    public void visit(Assignment node) {
    	program.debugComment("Assignment beginns.");
    	program.debugComment("Handle destination.");
    	node.destination().accept(this);
    	program.debugComment("Handle source.");
    	node.source().accept(this);
    	Type type = typeChecker.getType(node);
        if (type instanceof ArrayType) {
        	// TODO 
        } else if (type.equivalent(new FloatType())) { 
        	program.popFloat("$f0");
        	program.appendInstruction("swc1 $f0, 0($t0)");
        } else { // Int
    		program.debugComment("Popping off value in asignmnet.");
    		program.popInt("$t0"); 
    		program.debugComment("Popping off destination address in assigmnet.");
        	program.popInt("$t1"); // dest addr
    		program.debugComment("Final assignment.");
        	program.appendInstruction("sw $t0, 0($t1)");
        }
    }

    @Override
    public void visit(Call node) {
        program.debugComment("Caller Setup");
        ExpressionList args = node.arguments(); // TODO instread args.accept(this)??????
		if (args.size() > 0) {
			program.debugComment("Evaluate function arguments.");
        	ArrayList<Expression> revList = new ArrayList<Expression>(args.list());
        	ListIterator<Expression> revItr = revList.listIterator(revList.size());
        	while (revItr.hasPrevious()) {
				revItr.previous().accept(this);
        	}
			program.debugComment("done -> Evaluate function arguments.");
    	}

        String funcName =  node.function().name();
        if (!funcName.matches("print(Bool|Float|Int|ln)|read(Float|Int)")) {
        	funcName = namespaceFunc(funcName);
        } else {
        	funcName = "func." + funcName;
        }
        program.appendInstruction("jal " + funcName);

		FuncType func = (FuncType) node.function().type();
 		if (!func.returnType().equivalent(new VoidType())) { // TODO avoid save value if noone uses it?
        	program.debugComment("Saving function return value at $v0 on the stack.");
			program.appendInstruction("subu $sp, $sp, 4");
			program.appendInstruction("sw $v0, 0($sp)");
 		}

        program.debugComment("Caller Teardown.");
        // TODO cleanup stack values from expressions like unused function calls (and used?).
        if (args.size() > 0) {
        	program.debugComment("Cleaning up used func args.");
        	int argSize = 0;
        	for (Expression expr : args) {
				Type type = typeChecker.getType((Command) expr);
        		argSize += type.numBytes();
        	}
			program.appendInstruction("addi $sp, $sp, " + argSize);
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
    	program.debugComment("Begin return func value.");
    	program.debugComment("done -> Begin return func value.");
    	node.argument().accept(this);
		//Type type = typeChecker.getType((Command) node.argument());
        //if (type.equivalent(new FloatType())) {
			//program.popFloat("$v0");
        //} else if (type.equivalent(new IntType()) || type.equivalent(new BoolType())) {
			//program.popInt("$v0");
        //} 
    }

    @Override
    public void visit(ast.Error node) {
        String message = "CodeGen cannot compile a " + node;
        errorBuffer.append(message);
        throw new CodeGenException(message);
    }

}
