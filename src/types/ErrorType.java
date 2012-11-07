package types;

public class ErrorType extends Type {
    
    private String message;
    
    public ErrorType(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    @Override
    public String toString()
    {
        return "ErrorType(" + message + ")";
    }
    
    @Override
    public boolean equivalent(Type that)
    {
        if (that == null)
            return false;
        if (!(that instanceof ErrorType))
            return false;
        
        return message.equals(((ErrorType)that).message);
    }
}
