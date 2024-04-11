package entities;

/**
* This class represents a supervisor who can supervise projects.
* It extends the User class and implements the IUser interface and IPassword interface.
*/
public class Supervisor extends User {
	/**
	 * attributes
	 */
    private String name;
    private String email;
    
    /**
     * if allocatedProjects is 2 then isAvailable should be false
     */
    private Boolean isAvailable;
    
    /**
     * this should be at most 2
     */
    private int allocatedProjectCount;

    /**
     * Constructs a new Supervisor object with the given name and email.
     * The isAvailable attribute is initialized to true, and allocatedProjectCount to 0.
     * @param name The name of the supervisor.
     * @param email The email of the supervisor.
     */
    public Supervisor(String name, String email) {
        super(name, email);
        this.name = name;
        this.email = email;
        this.isAvailable = true;
        this.allocatedProjectCount = 0;
    }

    /**
     * Getters
     */
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
	public boolean getIsAvailable() {return this.isAvailable;}
	
	/**
	 * Used to keep track of projects allocated to supervisor to determine if he/she is available
	 */
	public void incrementAllocatedProjectCount() {
		if (this.allocatedProjectCount < 2) {
			this.allocatedProjectCount++;
		}
		
		if (this.allocatedProjectCount == 2) {
			this.isAvailable = false;
		}

	}
	
	/**
	 * Used to keep track of projects allocated to supervisor to determine if he/she is available
	 */
	public void decrementAllocatedProjectCount() {
		if (this.allocatedProjectCount > 0) {
			this.allocatedProjectCount--;
		}
		
		if (this.allocatedProjectCount < 2) {
			this.isAvailable = true;
		}
	}
}
