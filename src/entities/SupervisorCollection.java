package entities;


/**
* This class represents a collection of supervisors.
* It extends the UserCollection class, which contains a collection of users.
*/
public class SupervisorCollection extends UserCollection {
	
	/**
	 * Creates a new SupervisorCollection object.
	 */
	private static SupervisorCollection instance = null;
	
	
	/**
	 * Creates a new SupervisorCollection object.
	 */
	private SupervisorCollection() {}
	
	/**
	 * Returns the singleton instance of the SupervisorCollection class.
	 * 
	 * @return The SupervisorCollection instance.
	 */
	public static synchronized SupervisorCollection getInstance() {
		if (instance == null) 
			instance = new SupervisorCollection();
		return instance;
	}
 	
	/**
	 * Inserts the specified user into the collection.
	 * 
	 * @param user The user to be inserted.
	 */
	public void insert(User user) {
		super.users.add(user);
	}

}
