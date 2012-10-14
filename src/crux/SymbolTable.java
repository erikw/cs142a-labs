package crux;

import java.util.Vector;

/**
 * A table of symbols.
 */
public class SymbolTable {

    /**
     * Construct a new symbol table.
     */
    public SymbolTable() {
        throw new RuntimeException("implement this");
    }

    /**
     * Look up a symbol name in the table.
     * @param name The symbol to look up.
     * @return The found symbol.
     * @throws SymbolNotFoundError when the symbols was not found.
     */
    public Symbol lookup(String name) throws SymbolNotFoundError {
        throw new RuntimeException("implement this");
    }

    /**
     * Get a symbols with a given name.
     * @param name The symbol name.
     * @return The symbol.
     */
    private Symbol get(String name) {
        throw new RuntimeException("implement this");
    }

    /**
     * Insert a new name in the table.
     * @param name The new symol name to insert.
     * @return The new symbol inserted.
     * @throws RedeclarationError if the symbol already existed.
     */
    public Symbol insert(String name) throws RedeclarationError {
        throw new RuntimeException("implement this");
    }

    /**
     * Get a string representation of the symbol table.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (parent != null) {
            sb.append(parent.toString());
        }

        String indent = new String();
        for (int i = 0; i < depth; i++) { indent += "  ";
        }

        for (Symbol s : table) {
            sb.append(indent + s.toString() + "\n");
        }
        return sb.toString();
    }
}

/**
 * An error representing the act of not finding a symbol in the table.
 */
class SymbolNotFoundError extends Error {
    private static final long serialVersionUID = 1L;

    /* The name of the not found symol. */
    private String name;

    /**
     * Construct a new error for a give symbol name.
     * @param The errornous symbol name.
     */
    SymbolNotFoundError(String name) {
        this.name = name;
    }

    /**
     * Get the name of the errornous symbol.
     * @return The symbol name.
     */
    public String name() {
        return name;
    }
}

/**
 * An error representing the act redeclaring an symbol.
 */
class RedeclarationError extends Error {
    private static final long serialVersionUID = 1L;

    /**
     * Construct a new error for the given symbol.
     * @parmam sym The redeclared symbol.
     */
    public RedeclarationError(Symbol sym) {
        super("Symbol " + sym + " being redeclared.");
    }
}
