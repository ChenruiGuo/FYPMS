package controllers;

import java.util.ArrayList;

import entities.Account;
import entities.ChangeTitleRequest;
import entities.CoordinatorCollection;
import entities.Request;
import entities.RequestCollection;
import entities.StudentCollection;
import entities.SupervisorCollection;
import entities.User;

/**
 * Controller class that handles requests related to authenticating a new user,
 * updating login credentials, or accessing and updating the currently logged in Account.
 */
public class AuthController {
	
	/**
	 * Checks if password is valid and returns a boolean if it is a valid password.
	 *
	 * @param pw the password to check.
	 * @return true if the password is valid; false otherwise.
	 */
	private static boolean checkPasswordSpecs(String pw){

		//if newPass does not pass, return false
		if(pw.length()<8) {
			System.out.println("New password too short!");
			return false;
		}
	
		int specchar=0;
		int lowcase=0;
		int upcase=0;
		int digit=0;
		for(int i=0;i<pw.length();i++) {
			char c=pw.charAt(i);
			if(Character.isUpperCase(c))upcase++;
			else if(Character.isLowerCase(c))lowcase++;
			else if(Character.isDigit(c))digit++;
			else specchar++;
		}
		
		//newPass passes, return 0
		if(specchar>0 && lowcase>0 && upcase>0 && digit>0)return true;
		
		if(specchar==0)System.out.println("New password needs at least one special character!");
		if(lowcase==0)System.out.println("New password needs at least one lowercase letter!");
		if(upcase==0)System.out.println("New password needs at least one uppercase letter!");
		if(digit==0)System.out.println("New password needs at least one numeric character!");
		return false;

	}
	
	/**
	 * Logs in a new user by setting it on the Account singleton of the app.
	 *
	 * @param userId the user ID to log in with.
	 * @param password the password to log in with.
	 * @return true if the login was successful; false otherwise.
	 */
	public static boolean login(String userId, String password) {
		StudentCollection students = StudentCollection.getInstance();
		User user;
		user = students.getUserById(userId);
		
		if (user == null) {
			CoordinatorCollection coordinators = CoordinatorCollection.getInstance();
			user = coordinators.getUserById(userId);
		}

		
		if (user == null) {
			SupervisorCollection supervisors = SupervisorCollection.getInstance();
			user = supervisors.getUserById(userId);
		}
		
		if (user == null)
			return false;
		
		if (!user.getPassword().equals(password))
			return false;
		
		Account.getInstance().setUser(user);
		Account.getInstance().setIsLoggedIn(true);
		
		return true;
		
	}


	/**
	 * Handles business logic for the changePassword functionality. If validation checks pass,
	 * then the password is set on the Account singleton's current user.
	 *
	 * @param oldPass the old password to compare.
	 * @param newPass the new password to set.
	 * @param confirmNewPass the new password to confirm.
	 * @return true if the password was changed successfully; false otherwise.
	 */
	public static boolean changePassword(String oldPass, String newPass, String confirmNewPass) {
		//check if old password is correct
		if (!Account.getInstance().getUser().getPassword().equals(oldPass)) {
			System.out.print("Your old password is wrong! ");
			return false;
		}
		//confirm new password
		if (!newPass.equals(confirmNewPass)) {
			System.out.print("New password mismatch! ");
			return false;
		}
		//check if new password == old password
		if (newPass.equals(oldPass)) {
			System.out.print("Nothing to change! ");
			return false;
		}
		// check password strength
		if (!checkPasswordSpecs(newPass)) {
			return false;
		}
		
		// else change password and return true;
		Account.getInstance().getUser().setPassword(newPass);
		return true;
	}

	/**
	 * Logs out the account by updating user on Account singleton
	 */
	public static void logout() {
		Account.getInstance().setUser(null);
		Account.getInstance().setIsLoggedIn(false);
	}
	
	/**
	 * Get the name of the authenticated user.
	 *
	 * @return the name of the authenticated user.
	 */
	public static String getAuthUserName() {
		return Account.getInstance().getUser().getName();
	}
	
	/**
	 * Get the ID of the authenticated user.
	 *
	 * @return the ID of the authenticated user.
	 */
	public static String getAuthUserId() {
		return Account.getInstance().getUser().getUserId();
	}
	
	/**
	 * Check if the Account is logged in.
	 *
	 * @return true if the user is logged in; false otherwise.
	 */
	public static boolean isUserLoggedIn() {
		return Account.getInstance().getIsLoggedIn();
	}
	
	
	/**
	 * Check if the authenticated supervisor has any pending requests.
	 *
	 * @return true if the supervisor has any pending requests; false otherwise.
	 */
	public static boolean authSupervisorHasPendingRequests() {
		// show pending tag if coordinator has pending change title requests belonging to him
		ArrayList<Request> requests = RequestCollection.getInstance().filter(request -> (request instanceof ChangeTitleRequest));
		for (Request request : requests) {
			ChangeTitleRequest r = (ChangeTitleRequest) request;
			if ((r.getSupervisorId().equals(AuthController.getAuthUserId())) && (r.getStatus().equals(Request.RequestStatus.PENDING))) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check if the authenticated coordinator has any pending requests.
	 *
	 * @return true if the coordinator has any pending requests; false otherwise.
	 */
	public static boolean authCoordinatorHasPendingRequest() {
		// show pending tag if coordinator has pending change title requests belonging to him
		ArrayList<Request> requests = RequestCollection.getInstance().filter(request -> (request instanceof ChangeTitleRequest));
		for (Request request : requests) {
			ChangeTitleRequest r = (ChangeTitleRequest) request;
			if ((r.getSupervisorId().equals(AuthController.getAuthUserId())) && (r.getStatus().equals(Request.RequestStatus.PENDING))) {
				return true;
			}
		}
		
		// for all other types show tag so long as there is a pending request
		ArrayList<Request> list2 = RequestCollection.getInstance().filter(request -> !(request instanceof ChangeTitleRequest) && (request.getStatus().equals(Request.RequestStatus.PENDING)));
		if (list2.size() != 0) {
			return true;
		}
		
		return false;
	}
	
	// get auth user
	public static User getAuthUser() {
		return Account.getInstance().getUser();
	}

}
