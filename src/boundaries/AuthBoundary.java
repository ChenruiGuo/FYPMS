package boundaries;

import entities.Coordinator;
import entities.Student;
import entities.Supervisor;
import utils.InputOutput;

import java.util.ArrayList;

import controllers.AuthController;
/**
 * A boundary class that provides the user interface and which
 * the user interacts with for logging into the FYPMS.
 *
 *
 */
public class AuthBoundary extends BasePageBoundary {
	/**
	 * When initialised, the constructor of AuthBoundary does the following:
	 * the attribute menuWelcomeMessage is set to a greeting,
	 * and the attribute options is populated with Log in and Quit.
	 */
	// constructor
	public AuthBoundary() {
		super.options = new ArrayList<UIOption>();
		super.setMenuWelcomeMessage("Welcome User! Please choose an option to continue...");
		super.options.add(new UIOption("Log in", () -> this.renderLogin()));
		super.options.add(new UIOption("Quit", () -> this.quit()));
	}
	
	@Override
	/**
	 * The display method contains the main logic of AuthBoundary.
	 * If the user is not logged in, it calls renderMenuOptions() to display
	 * the possible options of Log in and Quit. It then processes the user
	 * input and calls either renderLogin() or quit() methods.
	 * 
	 * If the user is logged in, it creates the different Boundary objects
	 * according to the object type of the authorised user.
	 */
	public void display() {		
		boolean quit = false;
		while (!quit) {
			if (!AuthController.isUserLoggedIn()) {
				super.renderMenuOptions();
		        int choice = InputOutput.getInt();
		        switch (choice) {
		          case 1:
		        	this.renderLogin();
		            break;
		          case 2:
		        	this.quit();
		            quit = true;
		            break;
		          default:
		            System.out.println("Invalid choice. Please try again.");
		            break;
		        } 
			} else {
				if (AuthController.getAuthUser() instanceof Student) {
					// show Student Boundary
					new StudentBoundary().display();
				} else if (AuthController.getAuthUser() instanceof Coordinator) {
					// show coordinator boundary
					new CoordinatorBoundary().display();
				} else if (AuthController.getAuthUser() instanceof Supervisor) {
					// show supervisor boundary
					new SupervisorBoundary().display();
				} 
			}
			
		}
		
	}
	/**
	 * Takes in the user inputs of userid and password
	 * and calls AuthController.login() to verify them.
	 */
	private void renderLogin() {
		InputOutput.printSpacer();
		System.out.println("Enter user id: ");
  	  	String userId = InputOutput.getString();
  	  	InputOutput.printSpacer();
  	  	System.out.println("Enter password: ");
  	  	String password = InputOutput.getString();
  	  	boolean isLoginSuccess = AuthController.login(userId, password);
  	  	if (isLoginSuccess) {
  	  	  InputOutput.printSpacer();
  		  System.out.println("Login success!! :)");
  	  	} else {
  	  		InputOutput.printSpacer();
  	  		System.out.println("Invalid user id or password.");
  	  	}
	}
	
	/**
	 * prints goodbye message and quits the fypms
	 */
	private void quit() {
		System.out.println("Exiting app...");
	}
}

