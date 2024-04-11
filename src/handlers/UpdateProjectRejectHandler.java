package handlers;

import entities.Project;
import entities.ProjectRequest;
import entities.Request;
import entities.Supervisor;
import exceptions.HandlerFailedException;

/**
 * A handler responsible for handling ProjectRequests that have been rejected and updating the
 * status of the associated project accordingly. If the supervisor is available, the status of the project will be set to AVAILABLE,
 * otherwise it will be set to UNAVAILABLE.
 */
public class UpdateProjectRejectHandler extends Handler {

	/**
	  * Updates the status of the associated project depending on the availability of the supervisor.
	  *
	  * @param request The request object to be processed.
	  * @throws HandlerFailedException if the handler fails to update the project status.
	  */
	@Override
	public void handleRequest(Request request) throws HandlerFailedException {
		
		if (request instanceof ProjectRequest) {
			ProjectRequest currentRequest = (ProjectRequest) request;
			Project project = currentRequest.getProject();
			Supervisor supervisor = currentRequest.getSupervisor();
			if (supervisor.getIsAvailable()) project.setStatus(Project.ProjectStatus.AVAILABLE);
			else project.setStatus(Project.ProjectStatus.UNAVAILABLE);
		}
		
		if (super.next != null) super.next.handleRequest(request);
	} 
	
}
