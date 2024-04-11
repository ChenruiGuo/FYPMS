package entities;

/**
 * Changing the title of a project
 */

public class ChangeTitleRequest extends Request {
	/**
	 * ID of the supervisor making the change 
	 */
	private String supervisorId;
	
	/**
	 * New title
	 */
	private String newTitle;

	/**
	 * Constructor
	 * @param requestorID
	 * @param newTitle
	 * @param supervisorId
	 */
	public ChangeTitleRequest(String requestorID, String newTitle, String supervisorId) {
		super(requestorID);
		this.newTitle = newTitle;
		this.supervisorId = supervisorId;
	}
	
	/**
	 * Get the new title
	 * @return new project title
	 */
	public String getNewTitle() {
		return this.newTitle;
	}
	
	/**
	 * Get the id for the supervisor making the change
	 * @return supervisor ID
	 */
	public String getSupervisorId() {
		return this.supervisorId;
	}

}
