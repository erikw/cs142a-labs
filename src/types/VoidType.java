package types;

public class VoidType extends Type {
    
    public VoidType()
    {
    }
    
    @Override
    public String toString()
    {
        return "void";
    }
    
    @Override
    public boolean equivalent(Type that)
    {
        if (that == null)
            return false;
        if (!(that instanceof VoidType))
            return false;
        return true;
    }
}
