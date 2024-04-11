package entities;


/**
 * An abstract class representing a user of the system.
 * Implements the IUser and IPassword interfaces.
 */
public abstract class User implements IUser, IPassword {
    /**
     * The name of the user.
     */
    protected String name;
    /**
     * The email address of the user.
     */
    protected String email;
    /**
     * The password of the user.
     */
    protected String password;
    /**
     * The ID of the user, extracted from their email address
     */
    protected String userId;

    /**
     * Constructor for the User class.
     * Initializes the name, email, and userId of the user.
     * Sets the password to a default value.
     *
     * @param name The name of the user.
     * @param email The email address of the user.
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.password = "password";
        this.userId = extractUsername(email);
    }

    /**
     * Returns the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() { return this.name; }
    
    /**
     * Returns the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() { return this.email; }
    
    /**
     * Validates whether the specified input password matches the user's password.
     *
     * @param inputPass The password to validate.
     * @return True if the input password matches the user's password, false otherwise.
     */
    public boolean validatePass(String inputPass) { return inputPass == this.password; }
    
    
    /**
     * Returns the ID of the user.
     *
     * @return The ID of the user.
     */
	public String getUserId() {
		return userId;
	}

    /**
     * Sets the ID of the user to the specified value.
     *
     * @param userId The new ID for the user.
     */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
    /**
     * Extracts the username portion of the specified email address.
     *
     * @param email The email address to extract the username from.
     * @return The username portion of the email address.
     */
	private static String extractUsername(String email) {
	    int index = email.indexOf('@');
	    if (index != -1) {
	        return email.substring(0, index);
	    }
	    return email;
	}

    /**
     * Returns the password of the user.
     *
     * @return The password of the user.
     */
	public String getPassword() {
		return password;
	}

    /**
     * Sets the password of the user to the specified value.
     *
     * @param password The new password for the user.
     */
	public void setPassword(String password) {
		this.password = password;
	}
}
