package kraheja.exception;

/**
 *  <p> Using this exception class, we can tell user for some database related issue occurred. </p> 
 * 
 * @author sazzad.alom
 * @version 1.0.0
 * @since 16-NOVEMBER-2023
 *
 */
public class OutRateRateException extends RuntimeException{

	private int statusCode;
	private static final long serialVersionUID = 1L;
	public OutRateRateException (String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}
	  public int getStatusCode() {
	        return statusCode;
	    }
}
