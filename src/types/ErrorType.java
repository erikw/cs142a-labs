package types;

/**
 * Type representing an error.
 */
public class ErrorType extends Type {
    
    /* An error message. */
    private String message;
    
    /**
     * Construct a new error type with a message.
     * @param message String describing the problem.
     */
    public ErrorType(String message) {
        this.message = message;
    }
    
    /**
     * Get the error message.
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return "ErrorType(" + message + ")";
    }
    
    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof ErrorType)) {
            return false;
        } else {
        return message.equals(((ErrorType) that).message);
    }
}
