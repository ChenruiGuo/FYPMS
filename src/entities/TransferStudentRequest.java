package entities;


/**
* This class represents a transfer student request.
* A transfer student request is a type of request that contains information about a project and a replacement supervisor.
* It extends the Request class, which contains information about the requestor.
*/
public class TransferStudentRequest extends Request {
	/**
	 * The ID of the supervisor who will replace the original supervisor of the project.
	 */
	private String replacementSupervisorId;
	

	/**
	 * The ID of the project for which the request is made.
	 */
	private int projectId;
	
	/**
	 * Creates a new TransferStudentRequest object with the specified requestor ID, replacement supervisor ID, and project ID.
	 * 
	 * @param requestorID The ID of the user who made the request.
	 * @param replacementSupervisorId The ID of the supervisor who will replace the original supervisor of the project.
	 * @param projectId The ID of the project for which the request is made.
	 */
	public TransferStudentRequest(String requestorID, String replacementSupervisorId, int projectId) {
		super(requestorID);
		this.replacementSupervisorId = replacementSupervisorId;
		this.projectId = projectId;
	}
	
	/**
	 * Returns the ID of the supervisor who will replace the original supervisor of the project.
	 * 
	 * @return The ID of the replacement supervisor.
	 */
	public String getReplacementSupervisorId() {
		return this.replacementSupervisorId;
	}
	
	/**
	 * Returns the ID of the project for which the request is made.
	 * 
	 * @return The ID of the project.
	 */
	public int getProjectId() {
		return this.projectId;
	}

}
