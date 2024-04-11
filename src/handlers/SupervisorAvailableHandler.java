package handlers;

import entities.Request;

import entities.Supervisor;
import entities.SupervisorCollection;
import entities.TransferStudentRequest;
import exceptions.HandlerFailedException;
import entities.ProjectRequest;


/**
 * A handler responsible for checking if a supervisor is available to take on a request.
 */
public class SupervisorAvailableHandler extends Handler {

	/**
	 * This method checks if a supervisor is available to take on a request by checking
	 * the availability status of the supervisor in the request object.
	 * @param request the request object to be processed
	 * @throws HandlerFailedException if the supervisor is not available to take on the request
	 */
	@Override
	public void handleRequest(Request request) throws HandlerFailedException {
		
		if(request instanceof ProjectRequest) {
			ProjectRequest r = (ProjectRequest) request;
			
			if (!r.getSupervisor().getIsAvailable()) {
				throw new HandlerFailedException();
			}
		}
		
		else if(request instanceof TransferStudentRequest) {
			TransferStudentRequest r = (TransferStudentRequest) request;
			SupervisorCollection sups = SupervisorCollection.getInstance();
			Supervisor s = (Supervisor) sups.getUserById(r.getReplacementSupervisorId());
			
			if (!s.getIsAvailable()) {
				throw new HandlerFailedException();
			}
		}
		
		// move to next handler
		if (super.next != null) super.next.handleRequest(request);
		
	}

}
