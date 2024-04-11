package boundaries;

import java.util.ArrayList;

import controllers.AuthController;
import controllers.ProjectController;
import controllers.RequestController;
import entities.ChangeTitleRequest;
import entities.DeregisterRequest;
import entities.Student;
import entities.TransferStudentRequest;
import utils.InputOutput;
import entities.Project;
import entities.Project.ProjectStatus;
import entities.ProjectRequest;
import entities.Request;
import entities.RequestCollection;

public class StudentBoundary extends UserBoundary {
		
	// constructor
	public StudentBoundary() {
		super.options = new ArrayList<UIOption>();
		super.setMenuWelcomeMessage("Welcome Student! Please choose an option to continue...");
		super.options.add(new UIOption("Change password", () -> super.renderChangePassowrd()));
		super.options.add(new UIOption("View available projects", () -> this.renderViewAvailableProjects()));
		super.options.add(new UIOption("Request for project", () -> this.renderSubmitProjectRegisterRequest()));
		super.options.add(new UIOption("View your own project", () -> this.renderViewOwnProject()));
		super.options.add(new UIOption("View request status and history", () -> this.renderViewRequestHistory()));
		super.options.add(new UIOption("Request to change the title", () -> this.renderSubmitChangeTitleRequest()));
		super.options.add(new UIOption("Request to deregister FYP", () -> this.renderSubmitDeregisterRequest()));
		super.options.add(new UIOption("Log out", () -> AuthController.logout()));
	}
					
	
	// render methods
	private void renderViewAvailableProjects() {
		// if student has registered for a project or is banned then he cannot view
		if (AuthController.getAuthUser() instanceof Student) {
			Student student = (Student) AuthController.getAuthUser();
			if (student.getRegisterStatus() == Student.StudentStatus.UNREGISTERED) {
				InputOutput.printPaddedText("Below are the available fyp projects:");
				// view available projects
				super.renderProjects(ProjectController.getAvailableProjects());

			} else if (student.getRegisterStatus() == Student.StudentStatus.BANNED) {
				InputOutput.printError("You are not allowed to make a selection again as you deregistered your FYP.");
			} else {

			}
		} else {
			System.out.println("current account is not a student!");
		}
		
		// press to exit
		InputOutput.printSpacer();
		InputOutput.pressToExit();
	}
	
	private void renderSubmitProjectRegisterRequest() {	
		// get student object
		Student student = (Student) AuthController.getAuthUser();
		
		// check whether student already has registered project. if so then show error and return
		if (student.getRegisterStatus().equals(Student.StudentStatus.REGISTERED)) {
			InputOutput.printError("Cannot register more than one project!");
			return;
		}
		
		// check if student is banned from registering
		if (student.getRegisterStatus().equals(Student.StudentStatus.BANNED)) {
			InputOutput.printError("You have been banned from registering for projects!");
			return;
		}
		
		//check if student has submitted a prior request
		ArrayList<Project> x = ProjectController.getProjectsByStatus(ProjectStatus.RESERVED);
		for (Project project : x) {
			if (project.getStatus()==ProjectStatus.RESERVED && project.getStudentId()==student.getUserId()) {
				InputOutput.printError("Calm down! You have already submitted a project request.");
				return;
			}
		}
		
		// select project id to send coordinator
		System.out.println("Enter projectId: ");
		int projectId = InputOutput.getInt();
		
		// if project id does not exist then show error and return
		if (!ProjectController.projectExists(projectId)) {
			InputOutput.printError("There is no such project with id " + projectId + ". Please try again");
			return;
		}
		
		// if project is unavailable then show error and return
		if (!ProjectController.projectAvailable(projectId)) {
			InputOutput.printError("You are not allowed to register for this project!");
			return;
		}
		
		// pass in userId and projectId to generate request
		ProjectRequest projectRequest = new ProjectRequest(AuthController.getAuthUserId(), projectId);
		boolean submitProjectSuccess = new RequestController().submitRequest(projectRequest);
		if (submitProjectSuccess) {
			System.out.println("Request has been sent Successfully! Please await approval");
		} else {
			InputOutput.printError("Oops! Something went wrong. Please try again later!");
		}
	}
	
	private void renderViewOwnProject() {
		Project p = ProjectController.getAuthStudentProject();
		if (p == null) {
			System.out.println("You do not have any registered project. Please register and check again...");
		} else {
			System.out.println("Your registered fyp project is:");
			System.out.println("Id: " + p.getProjectId() + " Title: " + p.getTitle());
		}
	}
	
	private void renderViewRequestHistory() {
		// view request status and history
		ArrayList<Request> requests = RequestController.getAllByRequestor(AuthController.getAuthUserId());
		if (requests.size() == 0) System.out.println("You have not submitted any requests...");
		else {
			System.out.println("You have "+requests.size()+" request(s) in your transaction history:");
			this.renderRequests(requests);
		}
	}
	
	private void renderSubmitChangeTitleRequest() {
		// submit change title request
		Project allocatedProject = ProjectController.getProjectByStudentId(AuthController.getAuthUserId());
		if (allocatedProject == null) {
			InputOutput.printError("Please register for a project, before requesting change title!");
			return;
		} 
		System.out.println("Enter a new title for your FYP");
		String newTitle = InputOutput.getString();
		ChangeTitleRequest changeTitleRequest = new ChangeTitleRequest(AuthController.getAuthUserId(), newTitle, allocatedProject.getSupervisorId());
		boolean changeTitleRequestSuccess = new RequestController().submitRequest(changeTitleRequest);
		if (changeTitleRequestSuccess) {
			System.out.println("Request has been sent Successfully! Please await approval");
		} else {
			InputOutput.printError("Oops! Something went wrong. Please try again later!");
		}
	}
	
	private void renderSubmitDeregisterRequest() {		
		// check if there is allocated project
		Project allocatedProject = ProjectController.getProjectByStudentId(AuthController.getAuthUserId());
		if (allocatedProject == null) {
			InputOutput.printError("Please register for a project, before requesting to deregister!");
			return;
		} 
		
		System.out.println("Are you sure you want to de-register your project?");
		System.out.println(
				"If your request is approved you will no longer be allowed to register for other projects");
		InputOutput.printSpacer();
		System.out.println("Enter 123 to continue or any other number to cancel");
		int areYouSure = InputOutput.getInt();
		if (areYouSure == 123) {
			DeregisterRequest deregisterRequest = new DeregisterRequest(AuthController.getAuthUserId());
			boolean deregisterRequestSuccess = new RequestController().submitRequest(deregisterRequest);
			if (deregisterRequestSuccess) {
				System.out.println("Request has been sent Successfully! Please await approval");
			} else {
				InputOutput.printError("Oops! Something went wrong. Please try again later!");
			}
			return;
		}
		System.out.println("Cancelled!");
	}

}
