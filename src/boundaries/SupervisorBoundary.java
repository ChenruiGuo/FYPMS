package boundaries;

import java.util.ArrayList;

import controllers.AuthController;
import controllers.ProjectController;
import controllers.RequestController;
import entities.ChangeTitleRequest;
import entities.Project;
import entities.Request;
import entities.TransferStudentRequest;
import utils.InputOutput;

public class SupervisorBoundary extends UserBoundary {
	
	/**
	 * constructor for the boundary registers the relevant menu options and their handlers
	 */
	public SupervisorBoundary() {
		super.options = new ArrayList<UIOption>();
		super.setMenuWelcomeMessage("Welcome Supervisor! Please select an option to continue...");
		super.options.add(new UIOption("Change password", () -> super.renderChangePassowrd()));
		super.options.add(new UIOption("Manage projects (View, Create and Update)", () -> this.renderManageProjects()));
		super.options.add(new UIOption("Manage pending requests", () -> this.renderManageRequests()));
		super.options.add(new UIOption("View request history", () -> this.renderViewRequestHistory()));
		super.options.add(new UIOption("Request to transfer student to a replacement supervisor", () -> this.renderStudentTransferRequest()));
		super.options.add(new UIOption("Log out", () -> AuthController.logout()));
	}
	
	/**
	 * display function of UserBoundary is replaced to handle NEW tag functionality
	 */
	@Override
	public void display() {
		int choice = 0;
		while (AuthController.isUserLoggedIn()) {
			if (AuthController.authSupervisorHasPendingRequests())  {
				super.options.get(2).setOptionMessage("Manage pending requests !!NEW!!");;
			} else {
				super.options.get(2).setOptionMessage("Manage pending requests");
			}
			this.renderMenuOptions();
			choice = InputOutput.getInt();
			if (choice < 1 || choice > options.size()) {
				System.out.println("Invalid choice. Please try again.");
			} else {
				options.get(choice - 1).getOptionHandler().run();
			}
		}
	}	
	
	/**
	 * renders view to manage supervisors projects
	 */
	private void renderManageProjects() {
		System.out.print("Hello "+AuthController.getAuthUserName()+"!");
		while(true) {
			ArrayList<Project> projects = ProjectController.getAuthSupervisorProjects();
			System.out.println("You currently have "+projects.size()+" projects in total. They are as follows:");
			super.renderProjects(projects);
			
			System.out.println("What would you like to do?");
			System.out.println("1. Create new project");
			System.out.println("2. Update existing project title");
			System.out.println("Enter any other number to go back");
			int command = InputOutput.getInt();
			switch (command) {
			case 1:
				// create projects
				System.out.println("Creating new project...");
				System.out.println("Enter a project title: ");
				String newTitle = InputOutput.getString();
				ProjectController.createNewProject(newTitle);
				System.out.println("Project successfully created!");
				continue;
			case 2:
				// update project
				System.out.println("Which project title would you like to change? (Enter project ID)");
				int projId = InputOutput.getInt();
				System.out.println("Enter a new title for project ID "+projId+" :");
				String newTitle2 = InputOutput.getString();
				boolean updateSuccess = ProjectController.updateProjectTitle(projId, AuthController.getAuthUserId(), newTitle2);
				if (updateSuccess) {
					System.out.println("Project renamed successfully!");
				} else {
					System.out.println("Error: Please make sure the project ID is valid and that the project is owned by you before trying again!");
				}
				continue;
			default:
				System.out.println("Exiting project manager...");
				return;
			}
		}
	}
	
	/**
	 * renders view to manage incoming requests for the supervisor
	 */
	private void renderManageRequests() {
		System.out.println("Hello "+AuthController.getAuthUserName()+", welcome to the requests manager.");
		ArrayList<Request> requests = RequestController.getAll();
		for(int i=0;i<requests.size();i++) {
			if (!(requests.get(i) instanceof ChangeTitleRequest)) {
				requests.remove(i);i--;continue;
			}
			ChangeTitleRequest r = (ChangeTitleRequest) requests.get(i);
			if (!r.getStatus().equals(Request.RequestStatus.PENDING) ||
				!r.getSupervisorId().equals(AuthController.getAuthUserId())) {
				requests.remove(i);i--;continue;
			}
		}
		if (requests.size()==0) {
			System.out.println("You have no pending requests. Exiting requests manager...");
			return;
		}
		while (true) {
			System.out.println("You have "+requests.size()+" project title change request(s) pending. They are as follows:");
			this.renderRequests(requests);
			
			System.out.println("Enter request id: ");
			int requestId = InputOutput.getInt();
			Request r1 = RequestController.getReqById(requestId);
			if (r1==null) {
				InputOutput.printError("Request id does not exist. Try again.");
				continue;
			}
			if (!(r1 instanceof ChangeTitleRequest)) {
				InputOutput.printError("Request id provided is not a change title request. Try again.");
				continue;
			}
			
			System.out.println("What would you like to do?");
			System.out.println("1. Approve request by id ");
			System.out.println("2. Reject request by id ");
			System.out.println("Enter any other number to go back");
			int choice = InputOutput.getInt();
			if (choice == 1) {
				new RequestController().handleApprove(requestId);
				System.out.println("Request Approved");
			}
			else {
				new RequestController().handleReject(requestId);
				System.out.println("Request Rejected");
			}
			
			//remove request from requests
			for (int i=0;i<requests.size();i++) {
				if (requests.get(i).getRequestID()==requestId) {
					requests.remove(i); break;
				}
			}
			
			if (requests.size()==0)break;
		}
		System.out.println("Exiting requests manager...");
	}
	
	/**
	 * renders view to display request history
	 */
	private void renderViewRequestHistory() {
		System.out.println("Hello "+AuthController.getAuthUserName()+", welcome to the requests manager.");
		ArrayList<Request> requests1 = RequestController.getAll();
		for(int i=0;i<requests1.size();i++) {
			if (requests1.get(i) instanceof ChangeTitleRequest) {
				ChangeTitleRequest r = (ChangeTitleRequest) requests1.get(i);
				if (!r.getSupervisorId().equals(AuthController.getAuthUserId())) {
					requests1.remove(i);i--;continue;
				}
			}
			else if (requests1.get(i) instanceof TransferStudentRequest) {
				TransferStudentRequest r = (TransferStudentRequest) requests1.get(i);
				if (!r.getRequestor().equals(AuthController.getAuthUserId())) {
					requests1.remove(i);i--;continue;
				}
			}
			else {
				requests1.remove(i);i--;continue;
			}
		}
		if (requests1.size()==0) {
			System.out.println("You have no requests in your history. Exiting requests manager...");
			return;
		}
		System.out.println("You have "+requests1.size()+" total requests in your history. They are as follows:");
		this.renderRequests(requests1);
	}
	
	/**
	 * renders view to create a new request to transfer allocated student to another project
	 */
	private void renderStudentTransferRequest() {
		System.out.println("Creating a new request to transfer student to a replacement supervisor");
		System.out.println("Enter the project ID of the project you wish to transfer: ");
		int xferProjectId = InputOutput.getInt();
		//verify project belongs to supervisor
		Project transferProject = ProjectController.getProjectById(xferProjectId);
		
		// if no such project then show error and exit
		if (transferProject == null) {
			InputOutput.printError("No project with id " + xferProjectId + " exists!");
			return;
		}
		
		if (!transferProject.getSupervisorId().equals(AuthController.getAuthUserId())) {
			System.out.println("This project does not belong to you! Going back...");return;
		}
		System.out.println("Enter the supervisor ID of replacement supervisor: ");
		String replacementSupId = InputOutput.getString();
		TransferStudentRequest transferStudentRequest = new TransferStudentRequest(AuthController.getAuthUserId(), replacementSupId.toUpperCase(), xferProjectId);
		boolean requestSuccess = new RequestController().submitRequest(transferStudentRequest);
		if (requestSuccess) 
		{
			System.out.println("Request submitted successfully!");
		} else {
			System.out.println("Error: Please check that the project belongs to you and that it is allocated to a student");
		}
	}
	
}
