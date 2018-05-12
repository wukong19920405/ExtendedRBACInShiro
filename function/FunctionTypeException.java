package com.wukong.function;

public class FunctionTypeException extends Exception {

	/**
     * Constructs a new <code>FunctionTypeException</code> with no message or cause.
     */
    public FunctionTypeException() {

    }

    /**
     * Constructs a new <code>FunctionTypeException</code> with a message, but no cause. The message
     * is saved for later retrieval by the {@link java.lang#Throwable.getMessage()
     * Throwable.getMessage()} method.
     * 
     * @param message the detail message (<code>null</code> if nonexistent or unknown)
     */
    public FunctionTypeException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>FunctionTypeException</code> with a cause, but no message. The cause
     * is saved for later retrieval by the {@link java.lang#Throwable.getCause()
     * Throwable.getCause()} method.
     * 
     * @param cause the cause (<code>null</code> if nonexistent or unknown)
     */
    public FunctionTypeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new <code>FunctionTypeException</code> with a message and a cause. The message
     * and cause are saved for later retrieval by the {@link java.lang#Throwable.getMessage()
     * Throwable.getMessage()} and {@link java.lang#Throwable.getCause() Throwable.getCause()}
     * methods.
     * 
     * @param message the detail message (<code>null</code> if nonexistent or unknown)
     * @param cause the cause (<code>null</code> if nonexistent or unknown)
     */
    public FunctionTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
