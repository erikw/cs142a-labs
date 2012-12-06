package types;

import crux.Symbol;

/**
 * Type representing a boolean type.
 */
public class BoolType extends Type {

    /**
     * Construct a new boolean type.
     */
    public BoolType() {
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof BoolType)) {
            return false;
        } else {
        	return true;
    	}
	}

	@Override
    public Type and(Type that) {
        if (!(that instanceof BoolType)) {
            return super.and(that);
        } else {
        	return this;
        }
    }

	@Override
    public Type or(Type that) {
        if (!(that instanceof BoolType)) {
            return super.or(that);
        } else {
        	return this;
        }
    }

	@Override
    public Type not() {
        return this;
    }

	@Override
	public Type assign(Type source) {
		if (!equivalent(source)) {
			return super.assign(source);
		} else {
			return this;
		}
	}

	@Override
    public Type declare(Symbol symbol) {
       	return this;
    }

	@Override
	public Type baseType(Symbol symbol) {
		return this;
	}

	@Override
	public int numBytes() {
		return 4; 
	}
}    
