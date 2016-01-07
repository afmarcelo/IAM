package fr.mfa.iam.exceptions;

public class CustomException extends Exception{

	/**
	 *  Custom Exception.
	 */
	private static final long serialVersionUID = 1L;

	public CustomException(){}
	
	public CustomException(String message){
		super(message); // call super class constructor
	}
	
	public CustomException(Throwable cause)
	{
		super(cause);
	}
	
	public CustomException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
