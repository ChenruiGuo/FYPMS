package exceptions;

/**
 * This exception is thrown when a handler fails to handle a request.
 */
public class HandlerFailedException extends Exception {

	private static final long serialVersionUID = 5890356284232535403L;
	
	/**
	 * Constructs a new HandlerFailedException with a default error message.
	 */
	public HandlerFailedException() {
        super("Handler failed");
    }

}

