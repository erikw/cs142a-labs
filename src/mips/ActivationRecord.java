package mips;

import java.util.HashMap;

import crux.Symbol;
import types.*;

public class ActivationRecord {
    private static int fixedFrameSize = 2*4;
    private ast.FunctionDefinition func;
    private ActivationRecord parent;
    private int stackSize;
    private HashMap<Symbol, Integer> locals;
    private HashMap<Symbol, Integer> arguments;
    
    public static ActivationRecord newGlobalFrame() {
        return new GlobalFrame();
    }
    
    protected static int numBytes(Type type) {
        if (type instanceof IntType)
            return 4;
        if (type instanceof FloatType)
            return 4;
        if (type instanceof ArrayType) {
            ArrayType aType = (ArrayType)type;
            return aType.extent() * numBytes(aType.base());
        }
        throw new RuntimeException("No size known for " + type);
    }
    
    protected ActivationRecord() {
        this.func = null;
        this.parent = null;
        this.stackSize = 0;
        this.locals = null;
        this.arguments = null;
    }
    
    public ActivationRecord(ast.FunctionDefinition fd, ActivationRecord parent) {
        this.func = fd;
        this.parent = parent;
        this.stackSize = 0;
        this.locals = new HashMap<Symbol, Integer>();
        
        // map this function's parameters
        this.arguments = new HashMap<Symbol, Integer>();
        int offset = 0;
        for (int i=fd.arguments().size()-1; i>=0; --i) {
            Symbol arg = fd.arguments().get(i);
            arguments.put(arg, offset);
            offset += numBytes(arg.type());
        }
    }
    
    public String name() {
        return func.symbol().name();
    }
    
    public ActivationRecord parent() {
        return parent;
    }
    
    public int stackSize() {
        return stackSize;
    }
    
    public void add(Program prog, ast.VariableDeclaration var) {
        throw new RuntimeException("implement adding variable to local function space");
    }
    
    public void add(Program prog, ast.ArrayDeclaration array) {
        throw new RuntimeException("implement adding array to local function space");
    }
    
    public void getAddress(Program prog, String reg, Symbol sym) {
        throw new RuntimeException("implement accessing address of local or parameter symbol");
    }
}

class GlobalFrame extends ActivationRecord {
    public GlobalFrame() {
    }
    
    private String mangleDataname(String name) {
        return "cruxdata." + name;
    }
    
    @Override
    public void add(Program prog, ast.VariableDeclaration var) {
        throw new RuntimeException("implement adding variable to global data space");
    }    
    
    @Override
    public void add(Program prog, ast.ArrayDeclaration array) {
        throw new RuntimeException("implement adding array to global data space");
    }
        
    @Override
    public void getAddress(Program prog, String reg, Symbol sym) {
        throw new RuntimeException("implement accessing address of global symbol");
    }
}
