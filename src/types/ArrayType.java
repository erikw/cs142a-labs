package types;

/**
 * Type representing an array.
 */
public class ArrayType extends Type {

    /* Base of the array. */
    private Type base;

    /* How far from the base this array reaches. */
    private int extent;

    /**
     * Construct a new array type with extent and base
     * @param extent How far from the base this array reaches.
     * @param base The base of he array.
     */
    public ArrayType(int extent, Type base) {
        this.extent = extent;
        this.base = base;
    }

    /**
     * Get the extend.
     * @return The extend.
     */
    public int extent() {
        return extent;
    }

    /**
     * Get the base.
     * @return The base.
     */
    public Type base() {
        return base;
    }

    @Override
    public String toString() {
        return "array[" + extent + "," + base + "]";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof ArrayType)) {
            return false;
        } else {
        	ArrayType aType = (ArrayType) that;
        	return this.extent == aType.extent && base.equivalent(aType.base);
        }
    }

    @Override
    public Type index(Type amountType) {
        if (!amountType.equivalent(new IntType())) {
			return super.index(amountType);
        } else {
        	return base;
        }
    }

	@Override
    public Type deref() { // TODO should array override this?
        return base;
    }

	@Override
	public Type assign(Type source) {
		return base.assign(source);
	}
}
