package boundaries;

import java.util.ArrayList;

import controllers.AuthController;
import controllers.ProjectController;
import entities.ChangeTitleRequest;
import entities.DeregisterRequest;
import entities.Project;
import entities.ProjectRequest;
import entities.Request;
import entities.TransferStudentRequest;
import utils.InputOutput;

public abstract class UserBoundary extends BasePageBoundary {	
	
	/**
	 * Renders a list of projects.
	 *
	 * @param projects an ArrayList of Project objects
	 */
	protected void renderProjects(ArrayList<Project> projects) {
		for (Project project: projects) {
			int pid = project.getProjectId();
			Project.ProjectStatus status = project.getStatus();
			String studentId = project.getStudentId();
			String supervisorId = project.getSupervisorId();
			String title = project.getTitle();
						
			// print strings
			String pidStr = "ID: " + ((pid < 10) ? " " : "") + pid;
			String statusStr = "Status: " + status + ((status.equals(Project.ProjectStatus.RESERVED) ? " " : ""));
			String titleStr = "Title: " + title;
			String studentStr = "Student id: " + ((studentId == null) ? "No Student" : studentId);
			String supervisorStr = "Supervisor id: " + supervisorId;
			String sepStr = " | ";
			
			System.out.println(pidStr + sepStr + statusStr + sepStr + titleStr + sepStr + studentStr + sepStr + supervisorStr);

		}
	}
	
	/**
	 * Renders a list of requests.
	 *
	 * @param requests an ArrayList of Request objects
	 */
	protected void renderRequests(ArrayList<Request> requests) {
		for (Request request: requests) {
			
			//common attributes
			int reqid = request.getRequestID();
			Request.RequestStatus status = request.getStatus();
			String requestorid = request.getRequestor();
			
			//print strings for common attributes
			String reqidStr = "ID: " + ((reqid < 10) ? " " : "") + reqid;
			String statusStr = "Status: " + status + ((status.equals(Request.RequestStatus.PENDING) ? " " : ""));
			String requestoridStr = "Requestor id: " + requestorid;
			String sepStr = " | ";
			
			//ChangeTitleRequest
			if (request instanceof ChangeTitleRequest) {
				ChangeTitleRequest r = (ChangeTitleRequest) request;
				String supid = r.getSupervisorId();
				
				//print strings
				String typeStr = "Change Title Request";
				String supidStr = "Supervisor id: " + supid;
				String newtit = "New title: " + r.getNewTitle();
				
				System.out.println(reqidStr + sepStr + typeStr + sepStr + statusStr + sepStr + requestoridStr + sepStr + supidStr + sepStr + newtit);
				continue;
			}
			
			//DeregisterRequest
			else if (request instanceof DeregisterRequest) {
				DeregisterRequest r = (DeregisterRequest) request;
				
				//print strings
				String typeStr = "Deregister Request";
				
				System.out.println(reqidStr + sepStr + typeStr + sepStr + statusStr + sepStr + requestoridStr);
				continue;
			}
			
			//ProjectRequest
			else if (request instanceof ProjectRequest) {
				ProjectRequest r = (ProjectRequest) request;
				Project p = ProjectController.getProjectById(r.getProjectId());
				
				//print strings
				String typeStr = "Project Registration Request";
				String projidStr = "Project id: "+ r.getProjectId();
				String projnameStr = "Project name: " + p.getTitle();
				String supidStr = "Supervisor id: " + p.getSupervisorId();
				
				System.out.println(reqidStr + sepStr + typeStr + sepStr + statusStr + sepStr + requestoridStr + sepStr + projidStr + sepStr + projnameStr + sepStr + supidStr);
				continue;				
			}
			
			//TransferStudentRequest
			else {
				TransferStudentRequest r = (TransferStudentRequest) request;
				
				//print strings
				String typeStr = "Transfer Student Request";
				String projidStr = "Project id: "+ r.getProjectId();
				String supidStr = "New supervisor id: " + r.getReplacementSupervisorId();
				
				System.out.println(reqidStr + sepStr + typeStr + sepStr + statusStr + sepStr + requestoridStr + sepStr + projidStr + sepStr + supidStr);
				continue;	
			}
		}
	}
	
	/**
	 * Generates a new tag based on whether the given request is pending or not
	 * @param r
	 * @return String tag which either contains the word new or is empty
	 */
	protected String generateNewTag(Request r) {
		Request.RequestStatus status = r.getStatus();
		String newTag = (status.equals(Request.RequestStatus.PENDING)) ? " !!NEW!! " : "";
		return newTag;
	}
	
	/**
	 * generates user interface for changing password
	 */
	protected void renderChangePassowrd() {
		String oldPass;
		String newPass;
		String confirmNewPass;
		System.out.println("Enter current password: ");
		oldPass = InputOutput.getString();
		System.out.println("Enter new password: ");
		newPass = InputOutput.getString();
		System.out.println("Confirm password: ");
		confirmNewPass = InputOutput.getString();
		
		boolean changeSuccess = AuthController.changePassword(oldPass, newPass, confirmNewPass);
		
		if (changeSuccess) {
			// log out
			System.out.println("Password has been changed successfully. You will now be logged out. Please log in again to continue");
			AuthController.logout();
		} else {
			// show error message and return;
			System.out.println("Error please try again!");
		}
	}
}
