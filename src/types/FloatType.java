package types;

import crux.Symbol;

/**
 * Type representing a floating point.
 */
public class FloatType extends Type {

    /**
     * Construct a new floating point type.
     */
    public FloatType() {
    }

    @Override
    public String toString() {
        return "float";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof FloatType)) {
            return false;
        } else {
        	return true;
        }
    }

    @Override
    public Type add(Type that) {
        if (!(that instanceof FloatType)) {
            return super.add(that);
        } else {
        	return this;
        }
    }

    @Override
    public Type sub(Type that) {
        if (!(that instanceof FloatType)) {
            return super.sub(that);
        } else {
        	return this;
        }
    }

    @Override
    public Type mul(Type that) {
        if (!(that instanceof FloatType)) {
            return super.mul(that);

        } else {
        	return this;
        }
    }

    @Override
    public Type div(Type that) {
        if (!(that instanceof FloatType)) {
            return super.div(that);
        } else {
        	return this;
        }
    }

    @Override
    public Type compare(Type that) {
        if (!(that instanceof FloatType)) {
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
			return new VoidType();
		}
	}

	@Override
    public Type declare(Symbol symbol) {
       	return this;
    }

	@Override
	public boolean isValidBaseType() {
		return true;
	}
}
