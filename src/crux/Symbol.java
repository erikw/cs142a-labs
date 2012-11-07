package crux;

import types.Type;
import types.ErrorType;

public class Symbol {
    /* The name of the symbol. */
    private String name;

    /* The type of the symbol. */
    private Type type;

    /**
     * Construct a new symbol with a name.
     * @param name The symbol name.
     */
    public Symbol(String name) {
        this.name = name;
        this.type = new ErrorType("Type not set.");
    }

    /**
     * Get the name of this symbol.
     * @return The name of this symbol.
     */
    public String name() {
        return this.name;
    }
    
    /**
     * Set the type of this symbol.
     * @param type The new type of this symbol.
     */
    public void setType(Type type)
    {
        this.type = type;
    }

    /**
     * Get the type of this stymbol.
     * @return The type.
     */
    public Type type()
    {
        return type;
    }

    /**
     * Get a string represenation of this symbol.
     * @return A string representation.
     */
    public String toString() {
        if (Compiler.currentLab == Compiler.Lab.LAB5) {
        	return "Symbol(" + name + ":" + type + ")";
        } else {
        	return "Symbol(" + name + ")";
        }
    }

    /**
     * Make a new error symbol with a give message.
     * @param message The error message to use.
     * @return A new error symbol.
     */
    public static Symbol newError(String message) {
        return new ErrorSymbol(message);
    }
}

/**
 * An symbol representing an error.
 */
class ErrorSymbol extends Symbol {
    /**
     * Construct a new error symbol.
     */
    public ErrorSymbol(String message) {
        super(message);
    }
}
