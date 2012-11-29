package types;

import crux.Symbol;

/**
 * Abstract base type representing the types the can exist in a crux program.
 */
public abstract class Type {

    /**
     * Get the type indicated by the type string.
     * @param typeStr The string indicating the type.
     * @return The type.
     */
    public static Type getBaseType(String typeStr) {
        if (typeStr.equals("int")) {
			return new IntType();
        } else if (typeStr.equals("float")) {
			return new FloatType();
        } else if (typeStr.equals("bool")) {
			return new BoolType();
        } else if (typeStr.equals("void")) {
			return new VoidType();
        } else {
            //return new ErrorType("Unknown type: " + typeStr);
        	return new ErrorType("Unkown type: " + typeStr); // Public test files has this miss spelling.
        }
    }

    /**
     * Add this type to that.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param that The other operand.
     * @return Resulting type.
     */
    public Type add(Type that) {
        return new ErrorType("Cannot add " + this + " with " + that + ".");
    }

    /**
     * Substract this type to that.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param that The other operand.
     * @return Resulting type.
     */
    public Type sub(Type that) {
        return new ErrorType("Cannot subtract " + that + " from " + this + ".");
    }

    /**
     * Multiply this type to that.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param that The other operand.
     * @return Resulting type.
     */
    public Type mul(Type that) {
        return new ErrorType("Cannot multiply " + this + " with " + that + ".");
    }

    /**
     * Divide this type to that.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param that The other operand.
     * @return Resulting type.
     */
    public Type div(Type that) {
        return new ErrorType("Cannot divide " + this + " by " + that + ".");
    }

    /**
     * Logical and this type to that.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param that The other operand.
     * @return Resulting type.
     */
    public Type and(Type that) {
        return new ErrorType("Cannot compute " + this + " and " + that + ".");
    }

    /**
     * Logical or this type to that.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param that The other operand.
     * @return Resulting type.
     */
    public Type or(Type that) {
        return new ErrorType("Cannot compute " + this + " or " + that + ".");
    }

    /**
     * Logical not this type to that.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param that The other operand.
     * @return Resulting type.
     */
    public Type not() {
        return new ErrorType("Cannot negate " + this + ".");
    }

    /**
     * Compare this type to that.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param that The other operand.
     * @return Resulting type.
     */
    public Type compare(Type that) {
        return new ErrorType("Cannot compare " + this + " with " + that + ".");
    }

    /**
     * Dereference this type.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @return Resulting type.
     */
    public Type deref() {
        return new ErrorType("Cannot dereference " + this);
    }

    /**
     * Take index of this type.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @return Resulting type.
     */
    public Type index(Type that) {
        return new ErrorType("Cannot index " + this + " with " + that + ".");
    }

    /**
     * Call this type.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @return Resulting type.
     */
    public Type call(Type args) {
        return new ErrorType("Cannot call " + this + " using " + args + ".");
    }

    /**
     * Assign this type.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @return Resulting type.
     */
    public Type assign(Type source) {
        return new ErrorType("Cannot assign " + source + " to " + this + ".");
    }

    /**
     * Perform a structural equivalence test.
     * @param The other type to compare with.
     * @return Indication of equivalence.
     */
    public abstract boolean equivalent(Type that);

    /**
     * Declare a variable of this type.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param The symbol assosicated with the declaration.
     * @return Resulting type.
     */
    public Type declare(Symbol symbol) {
       	return new ErrorType("Variable " + symbol.name() + " has invalid type " + this + ".");
    }

	/**
	 * Get the base type of this type.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @param symbol The symbol for which we wants the base type of.
     * @return The base type.
	 */
	public Type baseType(Symbol symbol) {
		return new ErrorType("Array " + symbol.name() + " has invalid base type " + this + ".");
	}

	/**
	 * Get the number of bytes for this type.
     * Default implementation that is supposed to be overridden by types that should support this operation.
     * @return Number of types.
	 */
	public int numBytes() {
        throw new RuntimeException("No size known for " + this);
	}
}
