package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import entities.Coordinator;
import entities.CoordinatorCollection;
import entities.Project;
import entities.ProjectCollection;
import entities.Student;
import entities.StudentCollection;
import entities.Supervisor;
import entities.SupervisorCollection;

/**
 * A controller class for the initialisation of student, supervisor, coordinator
 * and project collections at the start of the App.
 * @author guoch
 *
 */
public class ApplicationLaunchController {
	/**
	 * Reads in student details from a txt file and creates a StudentCollection
	 * instance as a singleton.
	 * @param filepath The directory of the students txt file.
	 */
	public static void loadStudents(String filepath) {
        
        try {
        	StudentCollection collection = StudentCollection.getInstance();
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String [] data = myReader.nextLine().split("\\|");
                Student student = new Student(data[0], data[1]);
                collection.insert(student);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
	}
	/**
	 * Reads in supervisor details from a txt file and creates a SupervisorCollection
	 * instance as a singleton.
	 * @param filepath The directory of the supervisors txt file.
	 */
	public static void loadSupervisors(String filepath) {
        try {
        	SupervisorCollection collection = SupervisorCollection.getInstance();
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String [] data = myReader.nextLine().split("\\|");
                Supervisor supervisor = new Supervisor(data[0], data[1]);
                collection.insert(supervisor);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
	}
	/**
	 * Reads in coordinator details from a txt file and creates a CoordinatorCollection
	 * instance as a singleton.
	 * @param filepath The directory of the coordinator txt file.
	 */
	public static void loadCoordinators(String filepath) {
        try {
        	CoordinatorCollection collection = CoordinatorCollection.getInstance();
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String [] data = myReader.nextLine().split("\\|");
                Coordinator coord = new Coordinator(data[0], data[1]);
                collection.insert(coord);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
	}
	/**
	 * Reads in project details from a txt file and creates a ProjectCollection
	 * instance as a singleton.
	 * @param filepath The directory of the projects txt file.
	 */
	public static void loadProjects (String filepath) {
		ProjectCollection collection = ProjectCollection.getInstance();
	        try {
	            File myObj = new File(filepath);
	            Scanner myReader = new Scanner(myObj);
	            myReader.nextLine();
	            while (myReader.hasNextLine()) {
	                String [] data = myReader.nextLine().split("\\|");
	                String supervisorName = data[0];
	                String title = data[1];
	                String supervisorId = SupervisorCollection.getInstance().getFirstUserByName(supervisorName).getUserId();
	                Project project = new Project(title, supervisorId, null, Project.ProjectStatus.AVAILABLE);
	                collection.insert(project);
	            }
	            myReader.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("An error occurred.");
	            e.printStackTrace();
	        }
	}
	
}
