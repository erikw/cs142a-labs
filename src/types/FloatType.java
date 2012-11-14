package types;

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
        	return new FloatType();
        }
    }

    @Override
    public Type sub(Type that) {
        if (!(that instanceof FloatType)) {
            return super.sub(that);
        } else {
        	return new FloatType();
        }
    }

    @Override
    public Type mul(Type that) {
        if (!(that instanceof FloatType)) {
            return super.mul(that);

        } else {
        	return new FloatType();
        }
    }

    @Override
    public Type div(Type that) {
        if (!(that instanceof FloatType)) {
            return super.div(that);
        } else {
        	return new FloatType();
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
    public Type deref() {
        return new FloatType();
    }

    @Override
    public Type index(Type amountType) { // TODO realy take index of float type?
    	return new FloatType();
    }
}
