package crux;

//import java.util.Vector; // TODO huh?
import java.util.Map;
import java.util.LinkedHashMap;

// TODO implement null object pattern to make recusrion more beautifull!

/**
 * A table of symbols.
 */
public class SymbolTable {
	/* Functions that are available as a part of the crux language. */
    public static final String[] PREDEF_FUNCS = { 
        "readInt",
        "readFloat",
        "printBool",
        "printInt",
        "printFloat",
        "println"
    };

    /* The parent scope of this table. */
    private SymbolTable parent;

    /* The depth of this scope. */
    private int depth;

    /* The known symbols and names in this scope. */
    private Map<String, Symbol> table;

    /**
     * Construct a new symbol table.
     */
    public SymbolTable() {
        this(null);
    }

    /**
     * Construct a new symbol table with a parent.
     * @param parent The parent of this table.
     */
    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        depth = (parent == null) ? 0 : (parent.getDepth() + 1);
        table = new LinkedHashMap<String, Symbol>();
    }

    /**
     * Look up a symbol name in the table.
     * @param name The symbol to look up.
     * @return The found symbol.
     * @throws SymbolNotFoundError when the symbols was not found.
     */
    public Symbol lookup(String name) throws SymbolNotFoundError {
		Symbol target = get(name);
		if (target != null) {
			return target;
		} else {
			throw new SymbolNotFoundError(name);
		}
    }

    /**
     * Get a symbols with a given name.
     * @param name The symbol name.
     * @return The symbol.
     */
    private Symbol get(String name) {
        Symbol target = table.get(name);
        if (target == null && parent !=null) {
        	target = parent.lookup(name);
        } 
		return target;
    }

    /**
     * Insert a new name in the table.
     * @param name The new symol name to insert.
     * @return The new symbol inserted.
     * @throws RedeclarationError if the symbol already existed.
     */
    public Symbol insert(String name) throws RedeclarationError {
        Symbol symbol = table.get(name);
        if (symbol != null ) {
        	throw new RedeclarationError(symbol);
        } else {
			return table.put(name, new Symbol(name));
        }
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
        for (int i = 0; i < depth; i++) {
        	indent += "  ";
        }

        for (Symbol s : table.values()) {
            sb.append(indent + s.toString() + "\n");
        }
        return sb.toString();
    }

    /**
     * Get the parent of this table.
     * @return The parent symbol table of null if no sunch table exists.
     */
    public SymbolTable getParent() {
     	return parent;
    }

    /**
     * Get the depth of this scope.
     * @return The depth.
     */
    public int getDepth() {
     	return depth;
    }
}

// TODO visibillity?
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

// TODO visibillity?
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
