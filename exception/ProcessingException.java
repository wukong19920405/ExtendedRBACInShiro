package com.wukong.exception;

public class ProcessingException extends RuntimeException{

	public ProcessingException(){}
	
	public ProcessingException(String message){
		super(message);
	}
	
	public ProcessingException(Throwable cause){
		super(cause);
	}
	
	public ProcessingException(String message,Throwable cause){
		super(message,cause);
	}
}
