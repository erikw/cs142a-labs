package crux;

/**
 * Represenation of a symbol.
 */
public class Symbol {
    /* The name of the symbol. */
    private String name;

    /**
     * Construct a new symbol with a name.
     * @param name The symbol name.
     */
    public Symbol(String name) {
        this.name = name;
    }

    /**
     * Get the name of this symbol.
     * @return The name of this symbol.
     */
    public String name() {
        return this.name;
    }

    /**
     * Get a string represenation of this symbol.
     * @return A string representation.
     */
    public String toString() {
        return "Symbol(" + name + ")";
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

// TODO make private, inner?
/**
 * An smbol representing an error.
 */
class ErrorSymbol extends Symbol {
    /**
     * Construct a new error symbol.
     */
    public ErrorSymbol(String message) {
        super(message);
    }
}
