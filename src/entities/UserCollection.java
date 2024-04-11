package entities;

import java.util.ArrayList;

/**
 * A singleton class representing a collection of User objects.
 */
public class UserCollection {
	
    /**
     * The singleton instance of the UserCollection.
     */
	private static UserCollection instance = null;
	
    /**
     * The list of users in the collection.
     */
	protected ArrayList<User> users;
	
	
    /**
     * Constructor for the UserCollection class.
     * Initialises the list of users.
     */
	protected UserCollection() {
		users = new ArrayList<User>();
	}
	
    /**
     * Gets the singleton instance of the UserCollection.
     * If the instance does not yet exist, creates it.
     *
     * @return The singleton instance of the UserCollection.
     */
	public static synchronized UserCollection getInstance() {
		if (instance == null) 
			instance = new UserCollection();
		return instance;
	}
	
	
    /**
     * Returns the first user in the collection with the specified name.
     *
     * @param userName The name of the user to search for.
     * @return The first user in the collection with the specified name.
     *         Returns null if no user with the specified name is found.
     */
	public User getFirstUserByName (String userName) {
	    for (User user : users) {
	        if (user.getName().equals(userName)) {
	            return user;
	        }
	    }
	    return null; // If no user with matching name is found, return null
	}
	
    /**
     * Returns the user in the collection with the specified ID.
     *
     * @param userId The ID of the user to search for.
     * @return The user in the collection with the specified ID.
     *         Returns null if no user with the specified ID is found.
     */
	public User getUserById (String userId) {
		for (User u : users) 
			if (u != null && u.getUserId().toLowerCase().equals(userId.toLowerCase())) return u;
		
		return null;
	}
		
}
