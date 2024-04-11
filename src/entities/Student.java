package entities;

public class Student extends User {
    private StudentStatus registerStatus;
    
    public static enum StudentStatus {
    	UNREGISTERED,
    	REGISTERED,
    	BANNED
    }

    /**
     * Constructor for the Student class.
     * @param name The name of the student.
     * @param email The email address of the student.
     */
    public Student(String name, String email) {
        super(name, email);
        this.name = name;
        this.email = email;
        this.registerStatus = StudentStatus.UNREGISTERED;
    }

    /**
     * Gets the registration status of the student.
     * @return The registration status of the student.
     */
    public StudentStatus getRegisterStatus() { return this.registerStatus; }
    
    /**
     * Sets the registration status of the student.
     * @param registerStatus The new registration status of the student.
     */
    public void setRegisterStatus(StudentStatus registerStatus) { this.registerStatus = registerStatus; }
}
