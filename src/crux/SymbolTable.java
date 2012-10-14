package crux;

import java.util.Vector;

public class SymbolTable {
    
    public SymbolTable()
    {
        throw new RuntimeException("implement this");
    }
    
    public Symbol lookup(String name) throws SymbolNotFoundError
    {
        throw new RuntimeException("implement this");
    }
    
    private Symbol get(String name)
    {
        throw new RuntimeException("implement this");
    }
    
    public Symbol insert(String name) throws RedeclarationError
    {
        throw new RuntimeException("implement this");
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        if (parent != null)
            sb.append(parent.toString());
        
        String indent = new String();
        for (int i = 0; i < depth; i++) {
            indent += "  ";
        }
        
        for (Symbol s : table)
        {
            sb.append(indent + s.toString() + "\n");
        }
        return sb.toString();
    }
}

class SymbolNotFoundError extends Error
{
    private static final long serialVersionUID = 1L;
    private String name;
    
    SymbolNotFoundError(String name)
    {
        this.name = name;
    }
    
    public String name()
    {
        return name;
    }
}

class RedeclarationError extends Error
{
    private static final long serialVersionUID = 1L;

    public RedeclarationError(Symbol sym)
    {
        super("Symbol " + sym + " being redeclared.");
    }
}
