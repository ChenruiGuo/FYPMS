package handlers;

import entities.ProjectRequest;
import entities.Request;
import entities.Student;
import entities.StudentCollection;
import exceptions.HandlerFailedException;

/**
 * A handler that retrieves a student by ID.
 */
public class GetStudentHandler extends Handler {

	/**
	 * Handles a request by retrieving a student by ID.
	 *
	 * @param request The request to be handled.
	 * @throws HandlerFailedException If the handler fails to handle the request.
	 */
	@Override
	public void handleRequest(Request request) throws HandlerFailedException {
		
		if (request instanceof ProjectRequest) {
			
			ProjectRequest currentRequest = (ProjectRequest) request; 
			Student student = (Student) StudentCollection.getInstance().getUserById(currentRequest.getRequestor());
			currentRequest.setStudent(student);
			
			if (super.next != null) super.next.handleRequest(request);
			
		} 
		
	}

}
