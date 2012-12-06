package types;

import crux.Symbol;

/**
 * Type representing an interer.
 */
public class IntType extends Type {

	/**
	 * Construct a new interger type.
	 */
    public IntType() {
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof IntType)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Type add(Type that) {
        if (!(that instanceof IntType)) {
            return super.add(that);
        } else {
        	return this;
        }
    }

    @Override
    public Type sub(Type that) {
        if (!(that instanceof IntType)) {
            return super.sub(that);
        } else {
        	return this;
        }
    }

    @Override
    public Type mul(Type that) {
        if (!(that instanceof IntType)) {
            return super.mul(that);

        } else {
        	return this;
        }
    }

    @Override
    public Type div(Type that) {
        if (!(that instanceof IntType)) {
            return super.div(that);
        } else {
        	return this;
        }
    }

    @Override
    public Type compare(Type that) {
        if (!(that instanceof IntType)) {
            return super.compare(that);
        } else {
        	return new BoolType();
        }
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
