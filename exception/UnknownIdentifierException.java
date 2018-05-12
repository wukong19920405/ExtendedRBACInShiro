package com.wukong.exception;



	public class UnknownIdentifierException extends Exception {

	    /**
	     * Creates an <code>UnknownIdentifierException</code> with no data
	     */
	    public UnknownIdentifierException() {

	    }

	    /**
	     * Creates an <code>UnknownIdentifierException</code> with a message
	     * 
	     * @param message the message
	     */
	    public UnknownIdentifierException(String message) {
	        super(message);
	    }

	}


