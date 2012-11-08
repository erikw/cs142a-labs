package types;

/**
 * Type representing a function.
 */
public class FuncType extends Type {
   /* The argument types for the function. */
   private TypeList args;

   /* Returnt type for the function. */
   private Type ret;
   
   /**
   	* Construct a new function type with arguments and return type.
   	* @param args The function arguments.
   	* @para returnType The return type.
   	*/
   public FuncType(TypeList args, Type returnType) {
      this.args = args;
      this.ret = returnType;
   }
   
   /**
   	* Get the return type.
   	* @return The return type.
   	*/
   public Type returnType() {
      return ret;
   }
   
   /**
   	* Get the arguments.
   	* @return The arguments.
   	*/
   public TypeList arguments() {
      return args;
   }
   
   @Override
   public String toString() {
      return "func(" + args + "):" + ret;
   }

   @Override
   public boolean equivalent(Type that) {
      if (that == null) {
         return false;
      } else if (!(that instanceof FuncType)) {
         return false;
      } else {
      	  FuncType aType = (FuncType) that;
      	  return this.ret.equivalent(aType.ret) && this.args.equivalent(aType.args);
      }
   }

   @Override
   public Type call(Type args) {
        if (!(args instanceof TypeList)) { // TODO is call invoked with TypeList?
            return super.call(args);
        } else if (!((TypeList) args).equivalent(this.args)) {
 //* "Function " + func.name() + " has a void argument in position " + pos + "."
 //* "Function " + func.name() + " has an error in argument in position " + pos + ": " + error.getMessage()
            //return ...error node;
            return null; // Some type of error? if so, the same error strings as in error in funcdeclaration?
        } else {
			return ret;
        }
   }
}
