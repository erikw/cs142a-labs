package types;

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
        	return new IntType(); // TODO return this to not create unnecessary objects?
        }
    }

    @Override
    public Type sub(Type that) {
        if (!(that instanceof IntType)) {
            return super.sub(that);
        } else {
        	return new IntType();
        }
    }

    @Override
    public Type mul(Type that) {
        if (!(that instanceof IntType)) {
            return super.mul(that);

        } else {
        	return new IntType();
        }
    }

    @Override
    public Type div(Type that) {
        if (!(that instanceof IntType)) {
            return super.div(that);
        } else {
        	return new IntType();
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

    // TODO would solve index problem so we dont have to take instanceof but index on int IS wrong.
    //public Type index(Type that) {
        //return this;
    //}
 
	@Override
    public Type deref() {
        return new IntType();
    }

    @Override
    public Type index(Type amountType) { // TODO realy take index of int type?
    	return new IntType();
    }
}
