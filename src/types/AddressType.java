package types;

public class AddressType extends Type {
    
    private Type base;
    
    public AddressType(Type base) {
        throw new RuntimeError("implement operators");
        this.base = base;
    }
    
    public Type base() {
        return base;
    }

    @Override
    public String toString() {
        return "Address(" + base + ")";
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null)
            return false;
        if (!(that instanceof AddressType))
            return false;
        
        AddressType aType = (AddressType)that;
        return this.base.equivalent(aType.base);
    }
}
