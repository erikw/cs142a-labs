package types;

/**
 * Type representing an array.
 */
public class ArrayType extends Type {

    /* Base of the array. */
    private Type base;

    /* TODO */
    private int extent;

    /**
     * Construct a new array type with extent and base
     * @param extent TODO
     * @param base The base of he array.
     */
    public ArrayType(int extent, Type base) {
        throw new RuntimeError("implement operators");
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

    @override
    public string tostring() {
        return "array[" + extent "," + base + "]";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof IntType)) {
            return false;
        } else {
        	ArrayType aType = (ArrayType) that;
        	return this.extent == aType.extent && base.equivalent(aType.base);
        }
    }
}
