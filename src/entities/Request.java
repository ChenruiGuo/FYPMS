package entities;

/**
 * The Request class represents a request made by a user for some action to be taken.
 * The requestorID field represents the user who made the request.
 * The status field represents the current status of the request.
 * The requestID field is a unique identifier assigned to each request.
 */
public class Request {
	
	/**
	 * The RequestStatus enumeration represents the different statuses a request can have.
	 * APPROVED - the request has been approved
	 * REJECTED - the request has been rejected
 	 * PENDING - the request is pending approval or rejection
	 */
	public enum RequestStatus {
		APPROVED,
		REJECTED,
		PENDING;
	}
	
	protected String requestorID;
	protected RequestStatus status;
	private int requestID;
	
	/**
	 * Constructor for creating a new Request object.
	 * @param requestorID - the ID of the user who made the request
	 */
	public Request(String requestorID) {
		this.requestorID = requestorID;
		status = RequestStatus.PENDING;
	}
	
	/**
	 * Returns the ID of the user who made the request.
	 * @return the ID of the requestor
	 */
	public String getRequestor() {
		return requestorID;
	}
	
	/**
	 * Sets the ID of the user who made the request.
	 * @param requestorID - the ID of the requestor
	 */
	public void setRequestor(String requestorID) {
		this.requestorID = requestorID;
	}
	
	/**
	 * Returns the status of the request.
	 * @return the status of the request
	 */
	public RequestStatus getStatus() {
		return status;
	}
	
	/**
	 * Sets the status of the request.
	 * @param status - the new status of the request
	 */
	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	/**
	 * Returns the unique identifier for the request.
	 * @return the ID of the request
	 */
	public int getRequestID() {
		return requestID;
	}
	
	/**
	 * Sets the unique identifier for the request.
	 * @param requestID - the new ID for the request
	 */
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

}
