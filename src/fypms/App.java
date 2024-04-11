package fypms;

import java.util.Scanner;

import boundaries.AuthBoundary;
import controllers.ApplicationLaunchController;

/**
 * The main class for the FYP Management System application.
 */
public class App {
	
	public static final Scanner scanner = new Scanner(System.in);
	
	/**
	 * The entry point of the application.
	 */
	public static void main(String[] args) {
		// initialize application
		ApplicationLaunchController.loadStudents("src/fypms/student_list.txt");
		ApplicationLaunchController.loadSupervisors("src/fypms/faculty_list.txt");
		ApplicationLaunchController.loadCoordinators("src/fypms/FYP_coordinator.txt");
		ApplicationLaunchController.loadProjects("src/fypms/rollover_project.txt");
		
		// proceed with user authentication
		new AuthBoundary().display();
		
		//
		scanner.close();
	}
	
}
