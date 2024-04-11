package entities;

/**
 * The singleton instance of the StudentCollection class.
 */
public class StudentCollection extends UserCollection {
	
	private static StudentCollection instance = null;
	
	/**
	 * The private constructor of the StudentCollection class.
	 * This constructor is called only once during the first instantiation of the class.
	 */
	private StudentCollection() {}
	
	/**
	 * Returns the singleton instance of the StudentCollection class.
	 * If the instance does not exist, it is created and returned.
	 * @return The singleton instance of the StudentCollection class.
	 */

	public static synchronized StudentCollection getInstance() {
		if (instance == null) 
			instance = new StudentCollection();
		return instance;
	}
 	
	/**
	 * Inserts a new User object to the collection of users.
	 * The object must be an instance of the Student class.
	 * @param user The User object to be inserted.
	 */
	public void insert(User user) {
		super.users.add(user);
	}
	
}
