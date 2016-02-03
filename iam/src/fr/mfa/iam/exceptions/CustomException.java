package fr.mfa.iam.exceptions;

/**
 * CustomException class, it extends the Exception main Class.
 * @author marcelo
 */
public class CustomException extends Exception{
	/**
	 *  Custom Exception.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the class
	 */
	public CustomException(){}
	/**
	 * Custom Exception message.
	 * @param message
	 */
	public CustomException(String message){
		super(message); // call super class constructor
	}
	/**
	 * Custom Exception throwable
	 * @param cause
	 */
	public CustomException(Throwable cause)
	{
		super(cause);
	}
	/**
	 * Custom Exception, message and cause.
	 * @param message
	 * @param cause
	 */
	public CustomException(String message, Throwable cause)
	{
		super(message, cause);
	}
	/**
	 * Custom Exception.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
