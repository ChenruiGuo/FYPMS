package handlers;

import entities.Request;
import entities.Student;
import entities.TransferStudentRequest;
import entities.User;
import entities.UserCollection;
import exceptions.HandlerFailedException;
import controllers.ProjectController;
import entities.ChangeTitleRequest;
import entities.Project;
import entities.ProjectCollection;
import entities.ProjectRequest;

/**
 * A handler for handling various types of project update requests.
 * This includes updating the status of a project to "allocated" and setting the studentId, changing the
 * title of a project, and transferring a project to a new supervisor.
 */
public class UpdateProjectHandler extends Handler {

	@Override
	public void handleRequest(Request request) throws HandlerFailedException {
		
		/**
		 * Handles updating the status of a project to "allocated" and setting the studentId.
		 *
		 * @param request The request object to be processed.
		 * @throws HandlerFailedException if the handler fails to update the project.
		 */
		if (request instanceof ProjectRequest) {
			ProjectRequest r = (ProjectRequest) request;
			
			// mark project as allocated
			r.getProject().setStatus(Project.ProjectStatus.ALLOCATED);
			
			// set project studentId
			r.getProject().setStudentId(r.getStudent().getUserId());
		} 
		
		/**
		 * Handles changing the title of a project.
		 *
		 * @param request The request object to be processed.
		 * @throws HandlerFailedException if the handler fails to update the project title.
		 */
		else if (request instanceof ChangeTitleRequest) {
			ChangeTitleRequest r = (ChangeTitleRequest) request;
			//request comes from student ONLY
			int projectId = ProjectController.getProjectIdByStudentId(r.getRequestor());
			ProjectController.updateProjectTitleStudent(projectId, r.getRequestor(), r.getNewTitle());
		}
		
		/**
		 * Handles transferring a project to a new supervisor.
		 *
		 * @param request The request object to be processed.
		 * @throws HandlerFailedException if the handler fails to transfer the project to the new supervisor.
		 */
		else if (request instanceof TransferStudentRequest) {
			TransferStudentRequest r = (TransferStudentRequest) request;
			int projectId = r.getProjectId();
			ProjectCollection.getInstance().getById(projectId).setSupervisorId(r.getReplacementSupervisorId().toUpperCase());
		}

		// move to next handler
		if (super.next != null) super.next.handleRequest(request);
	}

}
