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
      throw new RuntimeException("implement operators");
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
}
