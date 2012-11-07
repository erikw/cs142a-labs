package types;

/**
 * Type representing a floating point.
 */
public class FloatType extends Type {
    
    /**
     * Construct a new floating point type.
     */
    public FloatType() {
        throw new RuntimeException("implement operators");
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
}
