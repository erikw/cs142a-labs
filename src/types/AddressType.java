package types;

/**
 * Type representing address of.
 */
public class AddressType extends Type {
    /* The base address. */
    private Type base;

    /**
     * Construct a new address type.
     * @param base The base address.
     */
    public AddressType(Type base) {
        this.base = base;
        throw new RuntimeException("implement operators");
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
}
