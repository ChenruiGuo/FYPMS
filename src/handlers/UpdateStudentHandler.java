package handlers;

import entities.Request;
import entities.Student;
import entities.StudentCollection;
import exceptions.HandlerFailedException;
import entities.DeregisterRequest;
import entities.ProjectRequest;

/**
 * A handler to update the student object associated with a request.
 * It sets the register status of the student to registered if the request is a ProjectRequest,
 * or to banned if the request is a DeregisterRequest.
 */
public class UpdateStudentHandler extends Handler {

	/**
	 * Sets the register status of the student to registered if the request is a ProjectRequest, 
	 * or to banned if the request is a DeregisterRequest.
	 *
	 * @param request The request object to be processed.
	 * @throws HandlerFailedException if the handler fails to update the student object.
	 */
	@Override
	public void handleRequest(Request request) throws HandlerFailedException {
		
		if(request instanceof ProjectRequest) {
			ProjectRequest x = (ProjectRequest)request;
			Student s = x.getStudent();
			s.setRegisterStatus(Student.StudentStatus.REGISTERED);
			
		}
		
		else if(request instanceof DeregisterRequest) {
			DeregisterRequest r = (DeregisterRequest) request;
			StudentCollection st = StudentCollection.getInstance();
			Student s = (Student) st.getUserById(r.getRequestor());
			s.setRegisterStatus(Student.StudentStatus.BANNED);
		}
		
		
		
		if (super.next != null) super.next.handleRequest(request);
	}

}
