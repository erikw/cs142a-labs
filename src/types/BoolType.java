package types;

public class BoolType extends Type {
    
    public BoolType() {
        throw new RuntimeError("implement operators");
    }
    
    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null)
            return false;
        if (!(that instanceof BoolType))
            return false;
        
        return true;
    }
}    
