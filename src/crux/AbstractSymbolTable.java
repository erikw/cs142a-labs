package crux;

/**
 * Interface for a symbol table.
 *
 * The purpose of this interface is to make recursion patterns in SymbolTable cleaner by using the null object pattern.
 */
@SuppressWarnings("unchecked") // Needed to suppress warning from whoIsMyFather. I know what I'm doing here.
public abstract class AbstractSymbolTable {

	/**
	 * A null object that does nothing and with inital depth of -1.
	 * Suitable for recursion.
	 */
	public static final AbstractSymbolTable NULL = new AbstractSymbolTable(-1) {

		public Symbol lookup(String name) {
			return null;
		}

		public String toString() {
			return "";
		}

    	/**
    	 * The father of a children with a NULL parent is always the child it self; think Adam and Eva.
    	 */
    	protected AbstractSymbolTable whoIsMyFather(AbstractSymbolTable child) {
    		return child; // Vader, I'm your father.
    	}
	};

    /* The depth of this scope. */
	protected int depth;

	protected AbstractSymbolTable(int depth) {
		this.depth = depth;
	}

    /**
     * Look up a symbol name in the table.
     * @param name The symbol to look up.
     * @return The found symbol.
     * @throws SymbolNotFoundError when the symbols was not found.
     */
    abstract public Symbol lookup(String name) throws SymbolNotFoundError;

    /**
     * Get a string representation of the symbol table.
     * @return A string representation of all the scopes.
     */
    public abstract String toString();

    /**
     * Answers the question of who is consider to be the parent of this table.
     *
     * This is needed so that the table that has a parent that is the NULL
     * instance is considered to be its own parent. This is because we don't
     * want to expose the implementation details (usage of NULL pattern) and we
     * should of course never use instanceof either.
     */
    protected abstract <T extends AbstractSymbolTable> T whoIsMyFather(AbstractSymbolTable child);
}
