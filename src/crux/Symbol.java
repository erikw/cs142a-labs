package crux;

public class Symbol {
    
    private String name;

    public Symbol(String name) {
        this.name = name;
    }
    
    public String name()
    {
        return this.name;
    }
    
    public String toString()
    {
        return "Symbol(" + name + ")";
    }

    public static Symbol newError(String message) {
        return new ErrorSymbol(message);
    }
}

class ErrorSymbol extends Symbol
{
    public ErrorSymbol(String message)
    {
        super(message);
    }
}
