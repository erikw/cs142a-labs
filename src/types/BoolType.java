package types;

/**
 * Type representing a boolean type.
 */
public class BoolType extends Type {

    /**
     * Construct a new boolean type.
     */
    public BoolType() {
        throw new RuntimeException("implement operators");
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
}    
