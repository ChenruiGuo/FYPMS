package entities;

/**
 * Collection of coordinators
 */
public class CoordinatorCollection extends UserCollection {
	
	/**
	 * Instance of coordinator collection
	 */
	private static CoordinatorCollection instance = null;
	
	private CoordinatorCollection() {}
	
	/**
	 * Get instance of collection, if null then create new instance
	 */
	public static synchronized CoordinatorCollection getInstance() {
		if (instance == null) 
			instance = new CoordinatorCollection();
		return instance;
	}
 	
	/**
	 * Add new user to the collection
	 * @param user
	 */
	public void insert(User user) {
		super.users.add(user);
	}
}
