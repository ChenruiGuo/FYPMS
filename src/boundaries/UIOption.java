package boundaries;

/**
* The UIOption class represents an option in a user interface with a message and a handler.
* The message is the text displayed to the user, and the handler is the code that is executed when the option is selected.
*/
public class UIOption {
	/**
	* The message displayed to the user for this option.
	*/
	private String optionMessage;
	
	/**
	 * Code to be executed when the option is selected
	 */
	private final Runnable optionHandler;

	/**
	* Constructs a new UIOption with the given message and handler.
	* @param optionMessage The message to be displayed to the user for this option.
	* @param optionHandler The code to be executed when this option is selected.
	*/
	public UIOption(String optionMessage, Runnable optionHandler) {
		this.optionMessage = optionMessage;
		this.optionHandler = optionHandler;
	}

	/**
	 * Returns the message to be displayed to the user for this option.
	 * @return The message to be displayed to the user for this option.
	 */
	public String getOptionMessage() {
		return optionMessage;
	}
	
	/**
	 * returns code to render the view for this option
	 * @return the code to be executed when this option is selected
	 */
	public Runnable getOptionHandler() {
		return optionHandler;
	}
	
	/**
	 * updates the option message for this option
	 * @param message
	 */
	public void setOptionMessage(String message) {
		this.optionMessage = message;
	}
}
