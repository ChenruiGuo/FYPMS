package handlers;

import entities.Request;

import java.util.ArrayList;

import controllers.ProjectController;
import entities.DeregisterRequest;
import entities.Project;
import entities.ProjectCollection;
import entities.ProjectRequest;
import entities.Supervisor;
import entities.SupervisorCollection;
import entities.TransferStudentRequest;
import exceptions.HandlerFailedException;

/**
 * This class handles the updating of project and supervisor data when a student registers or deregisters from a project,
 * or a supervisor transfers from a project
 */

public class UpdateSupervisorProjectsHandler extends Handler {

	@Override
	public void handleRequest(Request request) throws HandlerFailedException {
		
		if (request instanceof ProjectRequest) {
			ProjectRequest r = (ProjectRequest) request;
			Supervisor projectSupervisor = r.getSupervisor();
			
			// increment supervisor count
			projectSupervisor.incrementAllocatedProjectCount();
			
			// if supervisor no longer available then we set all available projects to unavailable
			if (!projectSupervisor.getIsAvailable()) {
				ArrayList<Project> projects = ProjectCollection.getInstance().getAll();
				for (Project project : projects) {
					if (project.getSupervisorId().equals(projectSupervisor.getUserId()) && project.getStatus() == Project.ProjectStatus.AVAILABLE) {
						ProjectController.setProjectStatusById(project.getProjectId(), Project.ProjectStatus.UNAVAILABLE);
					}
				}
			}
		}
		
		else if (request instanceof DeregisterRequest) {
			DeregisterRequest r = (DeregisterRequest) request;
			Project p = ProjectController.getProjectByStudentId(r.getRequestor());
			SupervisorCollection sups = SupervisorCollection.getInstance();
			Supervisor projectSupervisor = (Supervisor) sups.getUserById(p.getSupervisorId());
			
			p.setStudentId(null);
			p.setStatus(Project.ProjectStatus.UNAVAILABLE);
			
			// decrement supervisor count
			projectSupervisor.decrementAllocatedProjectCount();
			
			// if supervisor becomes available then we set all unavailable projects to available
			if (projectSupervisor.getIsAvailable()) {
				ArrayList<Project> projects = ProjectController.getAll();
				for (Project project : projects) {
					if (project.getSupervisorId().equals(projectSupervisor.getUserId()) && project.getStatus() == Project.ProjectStatus.UNAVAILABLE) {
						ProjectController.setProjectStatusById(project.getProjectId(), Project.ProjectStatus.AVAILABLE);
					}
				}
			}
		}
		
		else if (request instanceof TransferStudentRequest) {
			TransferStudentRequest r = (TransferStudentRequest) request;
			SupervisorCollection sups = SupervisorCollection.getInstance();
			
			//new supervisor - increment count
			Supervisor newsup = (Supervisor) sups.getUserById(r.getReplacementSupervisorId());
			newsup.incrementAllocatedProjectCount();
			// if supervisor no longer available then we set all available projects to unavailable
			if (!newsup.getIsAvailable()) {
				ArrayList<Project> projects = ProjectController.getAll();
				for (Project project : projects) {
					if (project.getSupervisorId().equals(newsup.getUserId()) && project.getStatus() == Project.ProjectStatus.AVAILABLE) {
						ProjectController.setProjectStatusById(project.getProjectId(), Project.ProjectStatus.UNAVAILABLE);
					}
				}
			}
			
			//old supervisor - decrement count
			Project p = ProjectCollection.getInstance().getById(r.getProjectId());
			Supervisor oldsup = (Supervisor) sups.getUserById(p.getSupervisorId());
			oldsup.decrementAllocatedProjectCount();
			// if supervisor becomes available then we set all unavailable projects to available
			if (oldsup.getIsAvailable()) {
				ArrayList<Project> projects = ProjectController.getAll();
				for (Project project : projects) {
					if (project.getSupervisorId().equals(oldsup.getUserId()) && project.getStatus() == Project.ProjectStatus.UNAVAILABLE) {
						ProjectController.setProjectStatusById(project.getProjectId(), Project.ProjectStatus.AVAILABLE);
					}
				}
			}
		}
		
		
		
		
		// move to next handler
		if (super.next != null) super.next.handleRequest(request);
		
	}

}
