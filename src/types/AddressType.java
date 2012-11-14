package types;

/**
 * Type representing address of.
 */
// TODO when should this be used?
public class AddressType extends Type {
    /* The base address. */
    private Type base;

    /**
     * Construct a new address type.
     * @param base The base address.
     */
    public AddressType(Type base) {
        this.base = base;
    }

    /**
     * Get the base address.
     * @return The base type.
     */
    public Type base() {
        return base;
    }

    @Override
    public String toString() {
        return "Address(" + base + ")";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof AddressType)) {
            return false;
        } else {
        	AddressType aType = (AddressType) that;
        	return this.base.equivalent(aType.base);
        }
    }

	@Override
    public Type assign(Type source) {
        if (!source.equivalent(base)) {
        	return super.assign(source);
        } else {
        	return new VoidType();  // TODO TA's slides says so...
        }
    }

	@Override
    public Type deref() {
        return base;
    }

	// TODO eric:override index // TODO but that does not work since test13 [12,19] implies that AddressType.index() does not exists. OR it means that this method should detect that base is not array?
    @Override
    public Type index(Type amountType) {
        if (!amountType.equivalent(new IntType()) || !(base instanceof ArrayType)) { // TODO check fo not arraytype?
			return super.index(amountType);
        } else {
        	return base;
        }
    }



	@Override
    public Type add(Type that) {
        return base.add(that);
    }
    
	@Override
    public Type sub(Type that) {
        return base.sub(that);
    }
    
	@Override
    public Type mul(Type that) {
        return base.mul(that);
    }
    
	@Override
    public Type div(Type that) {
        return base.div(that);
    }
    
	@Override
    public Type and(Type that) {
        return base.and(that);
    }
    
	@Override
    public Type or(Type that) {
        return base.or(that);
    }
    
	@Override
    public Type not() {
        return base.not();
    }
    
	@Override
    public Type compare(Type that) {
        return base.compare(that);
    }

    // TOOD override assign?
    //public Type assign(Type source) {
    //}
}
