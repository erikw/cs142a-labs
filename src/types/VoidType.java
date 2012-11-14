package types;

/**
 * Type representing not a type.
 */
public class VoidType extends Type {

    /**
     * Construct a not a type.
     */
    public VoidType() {
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof VoidType)) {
            return false;
        } else {
            return true;
        }
    }

	@Override
    public Type deref() {
        return new VoidType();
    }
}
