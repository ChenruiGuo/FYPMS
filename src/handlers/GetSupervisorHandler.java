package handlers;

import entities.Project;
import entities.ProjectRequest;
import entities.Request;
import entities.Supervisor;
import entities.SupervisorCollection;
import exceptions.HandlerFailedException;

/**
 * A handler that retrieves a supervisor by ID.
 */
public class GetSupervisorHandler extends Handler {

	/**
	 * Retrieves the supervisor associated with the project in the ProjectRequest object.
	 *
	 * @param request The request object to be processed.
	 * @throws HandlerFailedException if the handler fails to retrieve the supervisor.
	 */
	@Override
	public void handleRequest(Request request) throws HandlerFailedException {
		
		if (request instanceof ProjectRequest) {
			
			ProjectRequest currentRequest = (ProjectRequest) request; 
			Project project = currentRequest.getProject();
			String supervisorId = project.getSupervisorId();
			Supervisor supervisor = (Supervisor) SupervisorCollection.getInstance().getUserById(supervisorId);
			currentRequest.setSupervisor(supervisor);
						
			if (super.next != null) super.next.handleRequest(currentRequest);
			
		} 
		
	}

}
