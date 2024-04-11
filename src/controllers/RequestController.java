package controllers;

import java.util.ArrayList;

import entities.ChangeTitleRequest;
import entities.DeregisterRequest;
import entities.Project;
import entities.ProjectCollection;
import entities.ProjectRequest;
import entities.Request;
import entities.RequestCollection;
import entities.Student;
import entities.StudentCollection;
import entities.Supervisor;
import entities.SupervisorCollection;
import entities.TransferStudentRequest;
import exceptions.HandlerFailedException;
import handlers.GetProjectHandler;
import handlers.GetStudentHandler;
import handlers.GetSupervisorHandler;
import handlers.Handler;
import handlers.RejectRequestHandler;
import handlers.SupervisorAvailableHandler;
import handlers.UpdateProjectHandler;
import handlers.UpdateProjectRejectHandler;
import handlers.ApproveRequestHandler;
import handlers.UpdateStudentHandler;
import handlers.UpdateSupervisorProjectsHandler;
import utils.InputOutput;

/**
 * The RequestController class contains methods to handle requests made by students
 * and supervisors, such as submitting requests and approving/rejecting them.
 * It also includes methods to get all requests, get requests by id, and get all
 * requests by requestor.
 */
public class RequestController {
	
	/**
	 * Returns all requests in the request collection.
	 * @return ArrayList of Request objects
	 */
	public static ArrayList<Request> getAll() {
		return RequestCollection.getInstance().getAll();
	}
	
	/**
	 * Returns a Request object with the specified ID.
	 * @param id the ID of the Request to retrieve
	 * @return Request object with the specified ID
	 */
	public static Request getReqById(int id) {
		return RequestCollection.getInstance().getById(id);
	}
	
	/**
	 * Returns all requests made by a specific requestor.
	 * @param id the ID of the requestor to retrieve requests for
	 * @return ArrayList of Request objects made by the specified requestor
	 */
	public static ArrayList<Request> getAllByRequestor(String id) {
		return RequestCollection.getInstance().filter(request -> request.getRequestor().equals(id));
	}
	
	/**
	 * Submits a project request made by a student, sets the project as reserved, and adds the request to the request collection.
	 * @param request the ProjectRequest object to submit
	 * @return true if the request was submitted successfully, false otherwise
	 */
	public boolean submitRequest(ProjectRequest request) {
		if (request.getRequestor() == null)
				return false;

		StudentCollection students = StudentCollection.getInstance();
		//requestor is not a Student
		if(!(students.getUserById(request.getRequestor()) instanceof Student))return false;
		Student requestor = (Student)students.getUserById(request.getRequestor());
		//Student requestor is not UNREGISTERED
		if (requestor.getRegisterStatus() != Student.StudentStatus.UNREGISTERED)return false;
		
		//set Project to reserved
		ProjectCollection projects = ProjectCollection.getInstance();
		Project project = projects.getById(request.getProjectId());
		if(project==null) {
			InputOutput.printError("No project with id " + request.getProjectId() + " exists!");return false;
		}
		project.setStatus(Project.ProjectStatus.RESERVED);
		project.setStudentId(request.getRequestor());
		
		//add Request to Collection
		RequestCollection.getInstance().insert(request);
		return true;
	}
	
	/**
	 * Submits a deregister request made by a student and adds the request to the request collection.
	 * @param request the DeregisterRequest object to submit
	 * @return true if the request was submitted successfully, false otherwise
	 */
	public boolean submitRequest(DeregisterRequest request) {
		if (request.getRequestor() == null) 
				return false;
		
		StudentCollection students = StudentCollection.getInstance();
		//requestor is not a Student
		if(!(students.getUserById(request.getRequestor()) instanceof Student))return false;
		Student requestor = (Student)students.getUserById(request.getRequestor());
		//Student requestor is not REGISTERED
		if (requestor.getRegisterStatus() != Student.StudentStatus.REGISTERED)return false;
		
		//add Request to Collection
		RequestCollection.getInstance().insert(request);
		return true;
	}
	
	/**
	 * Submits a request to change the title of a project.
	 * @param request the {@link ChangeTitleRequest} to be submitted
	 * @return true if the request is submitted successfully, false otherwise
	 */
	public boolean submitRequest(ChangeTitleRequest request) {
		if (request.getRequestor() == null) 
				return false;
		
		StudentCollection students = StudentCollection.getInstance();
		//requestor is not a Student
		if(!(students.getUserById(request.getRequestor()) instanceof Student))return false;
		Student requestor = (Student)students.getUserById(request.getRequestor());
		//Student requestor is not REGISTERED
		if (requestor.getRegisterStatus() != Student.StudentStatus.REGISTERED)return false;
		
		//add Request to Collection
		RequestCollection.getInstance().insert(request);
		return true;
	}
	
	/**
	 * Submits a request to transfer a project to a new supervisor.
	 * @param request the {@link TransferStudentRequest} to be submitted
	 * @return true if the request is submitted successfully, false otherwise
	 */
	public boolean submitRequest(TransferStudentRequest request) {
		if (request.getRequestor() == null) return false;
		
		SupervisorCollection supervisors = SupervisorCollection.getInstance();
		//requestor is not a Supervisor
		if(!(supervisors.getUserById(request.getRequestor()) instanceof Supervisor)) return false;
		//Supervisor requestor = (Supervisor)supervisors.getUserById(request.getRequestor());
		
		//verify Project is allocated
		ProjectCollection projects = ProjectCollection.getInstance();
		Project project = projects.getById(request.getProjectId());
		if(project==null) {
			InputOutput.printError("No project with id " + request.getProjectId() + " exists!");return false;
		}
		if(project.getStatus() != Project.ProjectStatus.ALLOCATED) return false;
		
		//verify Project belongs to requestor
		if(!project.getSupervisorId().equals(request.getRequestor())) return false;
		
		//verify new supervisor
		if(!(supervisors.getUserById(request.getReplacementSupervisorId()) instanceof Supervisor)) return false;
		//Supervisor newsup = (Supervisor)supervisors.getUserById(request.getReplacementSupervisorId());
		//if (!(newsup.getIsAvailable())) return false;
		
		//add Request to Collection
		RequestCollection.getInstance().insert(request);
		return true;
	}
	

	/**
	 * Handles the approval of a request with the given ID.
	 * 
	 * @param requestID the ID of the request to approve
	 * @throws HandlerFailedException if any handler fails to execute
	 */
	public void handleApprove(int requestID) {
		
		Request request = RequestCollection.getInstance().getById(requestID);
		
		if (request instanceof ProjectRequest) {
			
			Handler handler1 = new GetProjectHandler();
			
			// get supervisor
			Handler handler2 = new GetSupervisorHandler();
			
			// check supervisor available
			Handler handler3 = new SupervisorAvailableHandler();
			
			// get student handler
			Handler handler4 = new GetStudentHandler();
			
			// mark project as allocated + set project student id
			Handler handler5 = new UpdateProjectHandler();
			
			// update supervisor count + update supervisor projects. handler will check the supervisor.isAvailable. and update projects accordingly
			Handler handler6 = new UpdateSupervisorProjectsHandler();
			
			// update student status to registered
			Handler handler7 = new UpdateStudentHandler();
			
			// update request to approved 
			Handler handler8 = new ApproveRequestHandler();
			
			handler1.setNext(handler2);
			handler2.setNext(handler3);
			handler3.setNext(handler4);
			handler4.setNext(handler5);
			handler5.setNext(handler6);
			handler6.setNext(handler7);
			handler7.setNext(handler8);
			
			try {
				handler1.handleRequest(request);
			} catch (HandlerFailedException e) {
				
			}
			

		} else if (request instanceof ChangeTitleRequest) {
			
			// change project title
			Handler handler1 = new UpdateProjectHandler();
			
			// update request to approved 
			Handler handler2 = new ApproveRequestHandler();
			
			handler1.setNext(handler2);
			
			try {
				handler1.handleRequest(request);
			} catch (HandlerFailedException e) {
				
			}
			
		} else if (request instanceof DeregisterRequest) {
			
			// update supervisor count + update supervisor projects. handler will check the supervisor.isAvailable. and update projects accordingly
			Handler handler1 = new UpdateSupervisorProjectsHandler();
			
			// update student status to banned
			Handler handler2 = new UpdateStudentHandler();
			
			// update request to approved 
			Handler handler3 = new ApproveRequestHandler();
			
			handler1.setNext(handler2);
			handler2.setNext(handler3);
			
			try {
				handler1.handleRequest(request);
			} catch (HandlerFailedException e) {
				
			}
			
		} else if (request instanceof TransferStudentRequest) {
			
			// check supervisor available (new sup)
			Handler handler1 = new SupervisorAvailableHandler();
			
			// a)update new supervisor count + update supervisor projects. handler will check the supervisor.isAvailable. and update projects accordingly
			// b)update supervisor count + update supervisor projects. handler will check the supervisor.isAvailable. and update projects accordingly
			Handler handler2 = new UpdateSupervisorProjectsHandler();
			
			// update project supervisor id.
			Handler handler3 = new UpdateProjectHandler();
			
			// update request to approved 
			Handler handler4 = new ApproveRequestHandler();
			
			handler1.setNext(handler2);
			handler2.setNext(handler3);
			handler3.setNext(handler4);
			
			try {
				handler1.handleRequest(request);
			} catch (HandlerFailedException e) {
				
			}
			
			
		} else {
			System.out.println("Request type not recognised");
		}
		
	}
	
	/**
	 * Handles the rejection of a request with the given ID.
	 * 
	 * @param requestID the ID of the request to reject
	 * @throws HandlerFailedException if any handler fails to execute
	 */
	public void handleReject(int requestID) {
		
		Request request = RequestCollection.getInstance().getById(requestID);
		
		if (request instanceof ProjectRequest) {
			
			Handler handler1 = new GetProjectHandler();

			Handler handler2 = new GetSupervisorHandler();
			
			Handler handler3 = new UpdateProjectRejectHandler();
			
			Handler handler4 = new RejectRequestHandler();
			
			handler1.setNext(handler2);
			handler2.setNext(handler3);
			handler3.setNext(handler4);
			
			try {
				handler1.handleRequest(request);
			} catch (HandlerFailedException e) {
				
			}

		} else if (request instanceof ChangeTitleRequest || request instanceof DeregisterRequest || request instanceof TransferStudentRequest) {
			
			Handler handler = new RejectRequestHandler();
			
			try {
				handler.handleRequest(request);
			} catch (HandlerFailedException e) {
				
			}
			
		} else {
			System.out.println("Request type not recognised");
		}
		
	}

}
