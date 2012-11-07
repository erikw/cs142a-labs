package types;

public abstract class Type {
    
    public static Type getBaseType(String typeStr)
    {
        if (typeStr.equals("int")) return new IntType();
        if (typeStr.equals("float")) return new FloatType();
        if (typeStr.equals("bool")) return new BoolType();
        if (typeStr.equals("void")) return new VoidType();
        return new ErrorType("Unkown type: " + typeStr);
    }
    
    public Type add(Type that)
    {
        return new ErrorType("Cannot add " + this + " with " + that + ".");
    }
    
    public Type sub(Type that)
    {
        return new ErrorType("Cannot subtract " + that + " from " + this + ".");
    }
    
    public Type mul(Type that)
    {
        return new ErrorType("Cannot multiply " + this + " with " + that + ".");
    }
    
    public Type div(Type that)
    {
        return new ErrorType("Cannot divide " + this + " by " + that + ".");
    }
    
    public Type and(Type that)
    {
        return new ErrorType("Cannot compute " + this + " and " + that + ".");
    }
    
    public Type or(Type that)
    {
        return new ErrorType("Cannot compute " + this + " or " + that + ".");
    }
    
    public Type not()
    {
        return new ErrorType("Cannot negate " + this + ".");
    }
    
    public Type compare(Type that)
    {
        return new ErrorType("Cannot compare " + this + " with " + that + ".");
    }
    
    public Type deref()
    {
        return new ErrorType("Cannot dereference " + this);
    }
    
    public Type index(Type that)
    {
        return new ErrorType("Cannot index " + this + " with " + that + ".");
    }
    
    public Type call(Type args)
    {
        return new ErrorType("Cannot call " + this + " using " + args + ".");
    }
    
    public Type assign(Type source)
    {
        return new ErrorType("Cannot assign " + source + " to " + this + ".");
    }
    
    // Perform a structural equivalence test
    abstract public boolean equivalent(Type that);
}
