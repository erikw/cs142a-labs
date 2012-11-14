package types;

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
        	return new BoolType();
        }
    }

	@Override
    public Type or(Type that) {
        if (!(that instanceof BoolType)) {
            return super.or(that);
        } else {
        	return new BoolType();
        }
    }

	@Override
    public Type not() {
        return new BoolType();
    }

	@Override
    public Type deref() {
        return new BoolType();
    }

    @Override
    public Type index(Type amountType) { // TODO realy take index of bool type?
    	return new BoolType();
    }
}    
