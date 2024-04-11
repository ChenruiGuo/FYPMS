package entities;

/**
 * Coordinator details
 */
public class Coordinator extends User {
    /**
     * Coordinator's name
     */
    private String name;
    
    /**
     * Coordinator's email
     */
    private String email;
    
    /**
     * Constructor
     * @param name
     * @param email
     */
    public Coordinator(String name, String email) {
        super(name, email);
        this.name = name;
        this.email = email;
    }
    
    /**
     * Get coordinator's name
     * @return name
     */
    public String getName() { return this.name; }
    
    /**
     * Get coordinator's email
     * @return email
     */
    public String getEmail() { return this.email; }
}
