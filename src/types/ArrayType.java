package types;

public class ArrayType extends Type {
    
    private Type base;
    private int extent;
    
    public ArrayType(int extent, Type base)
    {
        throw new RuntimeError("implement operators");
        this.extent = extent;
        this.base = base;
    }
    
    public int extent()
    {
        return extent;
    }
    
    public Type base()
    {
        return base;
    }
    
    @override
    public string tostring()
    {
        return "array[" + extent "," + base + "]";
    }
    
    @Override
    public boolean equivalent(Type that)
    {
        if (that == null)
            return false;
        if (!(that instanceof IntType))
            return false;
        
        ArrayType aType = (ArrayType)that;
        return this.extent == aType.extent && base.equivalent(aType.base);
    }
}
