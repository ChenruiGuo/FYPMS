package boundaries;

import java.util.ArrayList; 

import controllers.AuthController;
import controllers.ProjectController;
import controllers.RequestController;
import controllers.SupervisorController;
import entities.ChangeTitleRequest;
import entities.DeregisterRequest;
import entities.Project;
import entities.ProjectRequest;
import entities.Request;
import entities.TransferStudentRequest;
import utils.InputOutput;

public class CoordinatorBoundary extends UserBoundary {
	
	public CoordinatorBoundary() {
		super.options = new ArrayList<UIOption>();
		super.setMenuWelcomeMessage("Welcome Coordinator! Please select an option to continue...");
		super.options.add(new UIOption("Change password", () -> super.renderChangePassowrd()));
		super.options.add(new UIOption("Manage my projects (View, Create and Update)", () -> this.renderManageProjects()));
		super.options.add(new UIOption("Manage pending requests", () -> this.renderManageRequests()));
		super.options.add(new UIOption("View request history", () -> this.renderViewRequestHistory()));
		super.options.add(new UIOption("Generate project reports", () -> this.renderProjectReports()));
		super.options.add(new UIOption("Log out", () -> AuthController.logout()));
	}
	
	@Override
	public void display() {
		int choice = 0;
		while (AuthController.isUserLoggedIn()) {
			if (AuthController.authCoordinatorHasPendingRequest())  {
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
	
	private void renderManageProjects() {
		System.out.print("Hello "+AuthController.getAuthUserName()+"!");
		while(true) {
			ArrayList<Project> projects = ProjectController.getAuthSupervisorProjects();
			System.out.println("You currently have "+projects.size()+" projects in total. They are as follows:");
			this.renderProjects(projects);
			
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
				//first check that user has project
				if (projects.size()==0) {
					InputOutput.printError("You have no projects to rename :(");
					System.out.println("Exiting project manager...");
					return;
				}
				
				System.out.println("Which project title would you like to change? (Enter project ID)");
				int projId = InputOutput.getInt();
				System.out.println("Enter a new title for project ID "+projId+" :");
				String newTitle2 = InputOutput.getString();
				boolean updateSuccess = ProjectController.updateProjectTitle(projId, AuthController.getAuthUserId(), newTitle2);
				if (updateSuccess) {
					System.out.println("Project renamed successfully!");
				} else {
					InputOutput.printError("Please make sure the project ID is valid and that the project is owned by you before trying again!");
				}
				continue;
			default:
				System.out.println("Exiting project manager...");
				return;
			}
		}
	}
	
	private void renderManageRequests() {
		ArrayList<Request> requests = RequestController.getAll();
		System.out.println("Hello "+AuthController.getAuthUserName()+", welcome to the requests manager.");
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("1. Manage Project Registration Requests");
			System.out.println("2. Manage Project Deregistration Requests");
			System.out.println("3. Manage Student Transfer Requests");
			System.out.println("4. Manage Project Change Title Requests");
			System.out.println("5. Manage ALL Requests");
			System.out.println("Enter any other number to go back");
			int command = InputOutput.getInt();
			switch (command) {
			case 1:
				ArrayList<Request> registrationRequests = new ArrayList<Request>();
				
				int oldestRequestId = Integer.MAX_VALUE;
				for (Request request : requests) {
					if (request instanceof ProjectRequest && request.getStatus() == Request.RequestStatus.PENDING) {
						oldestRequestId = Math.min(oldestRequestId, request.getRequestID());
						registrationRequests.add(request);
					}
				}
 				
				if (registrationRequests.size() == 0) {
					InputOutput.printPaddedText("There are no pending registration requests");
					return;
				}
				
				while(true) {
					InputOutput.printPaddedText("Below are the pending registration requests:");
					//print from registrationRequests
					this.renderRequests(registrationRequests);
					
					System.out.println("What would you like to do?");
					System.out.println("1. Approve request " + oldestRequestId);
					System.out.println("2. Reject request " + oldestRequestId);
					System.out.println("Enter any other number to go back");
					int choice = InputOutput.getInt();
					if (choice == 1) {
						new RequestController().handleApprove(oldestRequestId);
						System.out.println("Request Approved");
					}
					else if (choice == 2) {
						new RequestController().handleReject(oldestRequestId);
						System.out.println("Request Rejected");
					}
					break;
				}
				
				continue;
			case 2:
				
				ArrayList<Request> deregistrationRequests = new ArrayList<Request>();
				
				for (Request request : requests) {
					if (request instanceof DeregisterRequest && request.getStatus() == Request.RequestStatus.PENDING) {
						deregistrationRequests.add(request);
					}
				}
 				
				if (deregistrationRequests.size() == 0) {
					InputOutput.printPaddedText("There are no pending deregistration requests");
					return;
				}
				
				InputOutput.printPaddedText("Below are the pending deregistration requests:");
				//print from deregistrationRequests
				this.renderRequests(deregistrationRequests);
				
				while(true) {
					System.out.println("What would you like to do?");
					System.out.println("1. Approve request by id ");
					System.out.println("2. Reject request by id ");
					System.out.println("Enter any other number to go back");
					int choice = InputOutput.getInt();
					if (choice != 1 && choice !=2)break;
					System.out.print("Enter request id: ");
					int requestId = InputOutput.getInt();
					//check requestId is inside allRequests
					boolean found = false;
					for (Request request : deregistrationRequests) {
						if(request.getRequestID()==requestId)found = true;
					}
					if (found == false) {
						InputOutput.printError("Invalid request ID, check again!");
						continue;
					}
					
					if (choice == 1) {
						new RequestController().handleApprove(requestId);
						System.out.println("Request Approved");
					}
					else {
						new RequestController().handleReject(requestId);
						System.out.println("Request Rejected");
					}
					
					break;
				}
				
				continue;
			case 3:
				
				ArrayList<Request> transferRequests = new ArrayList<Request>();
				
				for (Request request : requests) {
					if (request instanceof TransferStudentRequest && request.getStatus() == Request.RequestStatus.PENDING) {
						transferRequests.add(request);
					}
				}
 				
				if (transferRequests.size() == 0) {
					InputOutput.printPaddedText("There are no pending transfer requests");
					return;
				}
				
				while(true) {
					InputOutput.printPaddedText("Below are the pending transfer requests:");
					//print from transferRequests
					this.renderRequests(transferRequests);
					
					System.out.println("Enter request id: ");
					int requestId = InputOutput.getInt();
					Request r1 = RequestController.getReqById(requestId);
					if (r1==null) {
						InputOutput.printError("Request id does not exist. Try again.");
						continue;
					}
					if (!(r1 instanceof TransferStudentRequest)) {
						InputOutput.printError("Request id provided is not a transfer request. Try again.");
						continue;
					}
					TransferStudentRequest r = (TransferStudentRequest) r1;
					
					if (!SupervisorController.getSupervisorAvailabilityById(r.getReplacementSupervisorId())) {
						System.out.println("Here is a very timely hint to inform you that the replacement supervisor has hit the cap. You should NOT hit 1 on the next prompt.");
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
					break;
				}

				continue;
			case 4:
				
			ArrayList<Request> changeTitleRequests = new ArrayList<Request>();
				
				for (Request request : requests) {
					if (request instanceof ChangeTitleRequest && request.getStatus() == Request.RequestStatus.PENDING) {
						changeTitleRequests.add(request);
					}
				}
 				
				if (changeTitleRequests.size() == 0) {
					InputOutput.printPaddedText("There are no pending change title requests");
					return;
				}
				
				InputOutput.printPaddedText("Below are the pending change title requests:");
				//print from changeTitleRequests
				this.renderRequests(changeTitleRequests);
				
				while(true) {
					System.out.println("What would you like to do?");
					System.out.println("1. Approve request by id ");
					System.out.println("2. Reject request by id ");
					System.out.println("Enter any other number to go back");
					int choice = InputOutput.getInt();
					if (choice == 1 || choice == 2) {
						System.out.print("Enter request id: ");
						int requestId = InputOutput.getInt();
						if (choice == 1) {
							new RequestController().handleApprove(requestId);
							System.out.println("Request Approved");
						}
						else {
							new RequestController().handleReject(requestId);
							System.out.println("Request Rejected");
						}
					}
					break;
				}
				
				continue;
			
			case 5:
			
			ArrayList<Request> allRequests = new ArrayList<Request>();
			
			for (Request request : requests) {
				if (request.getStatus() == Request.RequestStatus.PENDING) {
					allRequests.add(request);
				}
			}
				
			if (allRequests.size() == 0) {
				InputOutput.printPaddedText("There are no pending requests");
				return;
			}
			
			InputOutput.printPaddedText("Below are all the pending requests:");
			//print from allRequests
			this.renderRequests(allRequests);
			
			while(true) {
				System.out.println("What would you like to do?");
				System.out.println("1. Approve request by id ");
				System.out.println("2. Reject request by id ");
				System.out.println("Enter any other number to go back");
				int choice = InputOutput.getInt();
				if (choice == 1 || choice == 2) {
					System.out.print("Enter request id: ");
					int requestId = InputOutput.getInt();
					//check requestId is inside allRequests
					boolean found = false;
					for (Request request : allRequests) {
						if(request.getRequestID()==requestId)found = true;
					}
					if (found == false) {
						InputOutput.printError("Invalid request ID, check again!");
						continue;
					}
					
					if (choice == 1) {
						new RequestController().handleApprove(requestId);
						System.out.println("Request Approved");
					}
					else {
						new RequestController().handleReject(requestId);
						System.out.println("Request Rejected");
					}
				}
				break;
			}
			
			continue;
			
			default:
				System.out.println("Exiting project manager...");
				return;
			}
		}	
	}
	
	private void renderViewRequestHistory() {
		System.out.println("Hello "+AuthController.getAuthUserName()+", welcome to the requests manager.");
		ArrayList<Request> requests = RequestController.getAll();
		
		if (requests.size()==0) {
			System.out.println("There are no requests. Exiting requests manager...");
			return;
		}
		
		System.out.println("There are "+requests.size()+" total request(s) in history. They are as follows:");
		//for (Request request : requests) System.out.println("ID: " + request.getRequestID() + " type: " + request.getClass().getSimpleName() + " requestor: " + request.getRequestor() + " status; " + request.getStatus() + super.generateNewTag(request));
		//print from requests
		this.renderRequests(requests);
	}
	
	private void renderProjectReports() {
		ArrayList<Project> projects = new ArrayList<Project>();
		
		while(true) {
			System.out.println("What would you like to do?");
			System.out.println("1. View All Projects ");
			System.out.println("2. View Projects By Status ");
			System.out.println("3. View Projects By Supervisor ");
			System.out.println("Enter any other number to go back");
			int choice = InputOutput.getInt();
			switch(choice) {
				case 1:
					InputOutput.printPaddedText("All projects");
					projects = ProjectController.getAll();
					break;
				case 2:
					System.out.println("1. View Available Projects");
					System.out.println("2. View Allocated Projects");
					System.out.println("3. View Reserved Projects");
					System.out.println("4. View Unavailable Projects");
					System.out.println("Enter any other number to go back");
					int option = InputOutput.getInt();
					if (option == 1) {
						InputOutput.printPaddedText("Available Projects");
						projects = ProjectController.getProjectsByStatus(Project.ProjectStatus.AVAILABLE);
					} else if (option == 2) {
						InputOutput.printPaddedText("Allocated Projects");
						projects = ProjectController.getProjectsByStatus(Project.ProjectStatus.ALLOCATED);
					} else if (option == 3) {
						InputOutput.printPaddedText("Reserved Projects");
						projects = ProjectController.getProjectsByStatus(Project.ProjectStatus.RESERVED);
					} else if (option == 4) {
						InputOutput.printPaddedText("Unavailable Projects");
						projects = ProjectController.getProjectsByStatus(Project.ProjectStatus.UNAVAILABLE);
					}
					break;
				 case 3:
					 System.out.println("Enter supervisor id: ");
					 String supervisorId = InputOutput.getString();
					 projects = ProjectController.getProjectsBySupervisorId(supervisorId.toUpperCase());
					 InputOutput.printPaddedText("Projects supervised by " + supervisorId.toUpperCase());
					 break;
				default:
					return;
			}
			super.renderProjects(projects);
			//for (Project project : projects) {
			//	System.out.println("Id: " + project.getProjectId() + " Title: " + project.getTitle() + " Supervisor: " + project.getSupervisorId());
			//}
		}
		
	}

}
