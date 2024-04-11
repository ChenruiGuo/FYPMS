package handlers;

import controllers.ProjectController;
import entities.Project;
import entities.ProjectCollection;
import entities.ProjectRequest;
import entities.Request;
import exceptions.HandlerFailedException;

/**
 * A handler that retrieves a project by ID.
 */
public class GetProjectHandler extends Handler {

	/**
	 * Handles a request by retrieving a project by ID.
	 *
	 * @param request The request to be handled.
	 * @throws HandlerFailedException If the handler fails to handle the request.
	 */
	@Override
	public void handleRequest(Request request) throws HandlerFailedException {
		
		if (request instanceof ProjectRequest) {
			
			ProjectRequest currentRequest = (ProjectRequest) request; 
			int projectId = currentRequest.getProjectId();
			Project project = ProjectCollection.getInstance().getById(projectId);
			currentRequest.setProject(project);
			
			if (super.next != null) super.next.handleRequest(currentRequest);
			
		} 
		
	}

}
