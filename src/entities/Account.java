package entities;

/**
 * Stores the account instance and logged in status.
 */

public class Account {
	
	/**
	 * The instance of the account class
	 */
	private static Account instance;
	
	/**
	 * The login status of the user
	 */
	private boolean isLoggedIn = false;
	
	/**
	 * The details of the user
	 */
	private User user = null;
	
	private Account() {
		
	}
	
	/**
	 * Get the instance of the account, if does not exist, create new instance
	 * @return account instance
	 */
	public static Account getInstance() {
		if (instance == null) 
			instance = new Account();
		return instance;
	}
	
	/**
	 * Get the login status of account
	 * @return login status
	 */
	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}
	
	/**
	 * Gets the details of the logged-in user
	 * @return user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the details for the logged-in user
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	* Sets the login status
	* @param status
	*/
	public void setIsLoggedIn(boolean status) {
		isLoggedIn = status;
	}
	
	

}
