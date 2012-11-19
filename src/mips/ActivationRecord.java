package mips;

import java.util.HashMap;

import crux.Symbol;
import types.*;

/**
 * Models an activation record.
 */
public class ActivationRecord {
    /* TODO */
    private static int fixedFrameSize = 2*4;

    /* TODO */
    private ast.FunctionDefinition func;

    /* Reference to the parent record. */
    private ActivationRecord parent;

    /* TODO */
    private int stackSize;

    /* TODO */
    private HashMap<Symbol, Integer> locals;

    /* TODO */
    private HashMap<Symbol, Integer> arguments;

    /**
     * Get a new global activation frame.
     * @return A new activation record.
     */
    public static ActivationRecord newGlobalFrame() {
        return new GlobalFrame();
    }

    /**
     * Get the number of bytes for a given type.
     * @param type The type to check. Must be IntType, FloatType or ArrayType.
     * @return The size in bytes.
     * @throws RuntimeException if the type constraints does not hold.
     */
    protected static int numBytes(Type type) {
        if (type instanceof IntType) {
            return 4;
        } else if (type instanceof FloatType) {
            return 4;
        } else if (type instanceof ArrayType) {
            ArrayType aType = (ArrayType)type;
            return aType.extent() * numBytes(aType.base());
        } else {
        	throw new RuntimeException("No size known for " + type);
        }
    }

    /**
     * Construct a new activation record with default values on members.
     */
    protected ActivationRecord() {
        this.func = null;
        this.parent = null;
        this.stackSize = 0;
        this.locals = null;
        this.arguments = null;
    }

    /**
     * Construct a new activation record with a given function defintion and parent record.
     * @param fd A function defintion.
     * @param parent The parent record.
     */
    public ActivationRecord(ast.FunctionDefinition fd, ActivationRecord parent) {
        this.func = fd;
        this.parent = parent;
        this.stackSize = 0;
        this.locals = new HashMap<Symbol, Integer>();

        // map this function's parameters
        this.arguments = new HashMap<Symbol, Integer>();
        int offset = 0;
        for (int i=fd.arguments().size()-1; i>=0; --i) {
            Symbol arg = fd.arguments().get(i);
            arguments.put(arg, offset);
            offset += numBytes(arg.type());
        }
    }

	/**
	 * Get the name of the function
	 * @return The name.
	 */
    public String name() {
        return func.symbol().name();
    }

	/**
	 * Get the parent record.
	 * @return The parent record.
	 */
    public ActivationRecord parent() {
        return parent;
    }

	/**
	 * TODO
	 */
    public int stackSize() {
        return stackSize;
    }

	/**
	 * Add a variable declaration to a program.
	 * @param prog The program to use.
	 * @param var The variable declaration to add.
	 */
    public void add(Program prog, ast.VariableDeclaration var) {
        throw new RuntimeException("implement adding variable to local function space");
    }

	/**
	 * Add an array declaration to a program.
	 * @param prog The program to use.
	 * @param array The array declaration to add.
	 */
    public void add(Program prog, ast.ArrayDeclaration array) {
        throw new RuntimeException("implement adding array to local function space");
    }

	/**
	 * Get the address of a local or paramter symbol.
	 * @param prog The program to get from.
	 * @param reg TODO res is put here?
	 * @param sym the symbol to get address of.
	 */
    public void getAddress(Program prog, String reg, Symbol sym) {
        throw new RuntimeException("implement accessing address of local or parameter symbol");
    }
}

/**
 * A frame that is in the global space.
 */
class GlobalFrame extends ActivationRecord {

    public GlobalFrame() {
    }

	/**
	 * Generate a unique name in the crux namespace to not conflict with MIPS built ins.
	 * @param name The input name.
	 * @return A mangled version of input.
	 */
    // TODO need a func for Funcnames as well?
    private String mangleDataname(String name) {
        return "cruxdata." + name;
    }

    @Override
    public void add(Program prog, ast.VariableDeclaration var) {
        throw new RuntimeException("implement adding variable to global data space");
    }    

    @Override
    public void add(Program prog, ast.ArrayDeclaration array) {
        throw new RuntimeException("implement adding array to global data space");
    }

    @Override
    public void getAddress(Program prog, String reg, Symbol sym) {
        throw new RuntimeException("implement accessing address of global symbol");
    }
}
