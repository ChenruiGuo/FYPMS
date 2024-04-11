package controllers;

import java.util.ArrayList; 
import entities.Project;
import entities.Supervisor;
import entities.Project.ProjectStatus;
import entities.ProjectCollection;

/**
 * This class represents the controller for managing the projects in the system.
 * It provides methods for getting, filtering and manipulating the projects data.
 */
public class ProjectController {
	/**
	 * Returns a list of all the projects in the system.
	 * @return ArrayList of Project objects
	 */
	public static ArrayList<Project> getAll() {
		return ProjectCollection.getInstance().getAll();
	}
	
	/**
	 * Returns a project object for a given project ID.
	 * @param projectId the ID of the project to retrieve
	 * @return a Project object or null if the project is not found
	 */
	public static Project getProjectById(int projectId) {
		return ProjectCollection.getInstance().getById(projectId);
	}
	
	/**
	 * Returns a list of projects that match the specified project status.
	 * @param status the status to filter projects by
	 * @return ArrayList of Project objects
	 */
	public static ArrayList<Project> getProjectsByStatus(Project.ProjectStatus status) {
		return ProjectCollection.getInstance().filter(project -> project.getStatus().equals(status));
	}
	
	/**
	 * Returns a list of available projects.
	 * @return ArrayList of Project objects
	 */
	public static ArrayList<Project> getAvailableProjects() {
		return ProjectCollection.getInstance().filter(ProjectCollection.P_AVAILABLE);
	}
	
	/**
	 * Checks if a project exists in the system.
	 * @param projectId the ID of the project to check
	 * @return true if the project exists, false otherwise
	 */
	public static boolean projectExists(int projectId) {
		return !(ProjectCollection.getInstance().getById(projectId) == null);
	}
	
	/**
	 * Checks if a project is available for selection.
	 * @param projectId the ID of the project to check
	 * @return true if the project is available, false otherwise
	 */
	public static boolean projectAvailable(int projectId) {
		return ProjectCollection.getInstance().getById(projectId).getStatus().equals(Project.ProjectStatus.AVAILABLE);
	}
	
	/**
	 * Returns the project object for the given student ID.
	 * @param studentId the ID of the student to retrieve the project for
	 * @return a Project object or null if the student is not assigned to a project
	 */
	public static Project getProjectByStudentId(String studentId) {
		for (Project p : ProjectCollection.getInstance().getAll()) 
			if (p != null && (p.getStudentId() == studentId)) return p;

		return null;
	}
	
	/**
	 * Returns the project ID for the given student ID.
	 * @param studentId the ID of the student to retrieve the project ID for
	 * @return the project ID or -1 if the student is not assigned to a project
	 */
	public static int getProjectIdByStudentId(String studentId) {
		for (Project project : ProjectCollection.getInstance().getAll())
			if (project != null && (project.getStudentId() == studentId)) return project.getProjectId();
		
		return -1;
	}
	
	/**
	 * Sets the status of a project by its ID.
	 * @param pid the ID of the project to set the status for
	 * @param status the new status of the project
	 */
	public static void setProjectStatusById(int pid, ProjectStatus status) {
		ArrayList<Project> projects = ProjectCollection.getInstance().getAll();
		for (Project p : projects) 
			if (p != null && (p.getProjectId() == pid)) p.setStatus(status);
	}
	
    /**
     * Gets the Project object associated with the currently authenticated student account.
     * @return The Project object associated with the currently authenticated student account,
     * or null if the account does not have an associated project.
     */
	public static Project getAuthStudentProject() {
		return getProjectByStudentId(AuthController.getAuthUserId());
	}
	
	/**
     * Gets a list of Project objects associated with the currently authenticated supervisor account.
     * Throws an exception if the currently authenticated account is not a supervisor.
     * @return A list of Project objects associated with the currently authenticated supervisor account.
     */
	public static ArrayList<Project> getAuthSupervisorProjects() {
		// if instance of Account is not student then throw error
		// to do
		return ProjectCollection.getInstance().filter(project -> project.getSupervisorId().equals(AuthController.getAuthUserId()));
	}
	
	/**
     * Gets a list of Project objects associated with the given supervisor ID.
     * @param supervisorId The ID of the supervisor whose projects to retrieve.
     * @return A list of Project objects associated with the given supervisor ID.
     */
	public static ArrayList<Project> getProjectsBySupervisorId(String supervisorId) {
		return ProjectCollection.getInstance().filter(project -> project.getSupervisorId().equals(supervisorId));
	}
	
		
	/**
     * Creates a new Project object with the given title and adds it to the system.
     * If the currently authenticated account is not a supervisor, an exception is thrown.
     * @param title The title of the new project.
     */
	public static void createNewProject(String title) {
		// check authentication state for supervisor
		Supervisor sup = (Supervisor) AuthController.getAuthUser();
		String sid = sup.getUserId();
		if (sup.getIsAvailable()) {
			// else add new project as available
			ProjectCollection.getInstance().insert(new Project(title, sid, null, Project.ProjectStatus.AVAILABLE));
		} else {
			ProjectCollection.getInstance().insert(new Project(title, sid, null, Project.ProjectStatus.UNAVAILABLE));
		}
		
	}
	
	/**
     * Updates the title of the project with the given ID, subject to certain checks.
     * The title can only be updated by a supervisor whose ID matches the project's supervisor ID.
     * @param pid The ID of the project to update.
     * @param sid The ID of the supervisor attempting to update the project.
     * @param newTitle The new title for the project.
     * @return true if the update was successful, false otherwise.
     */
	public static boolean updateProjectTitle(int pid, String sid, String newTitle) {
		Project project = ProjectCollection.getInstance().getById(pid);
		
		// if project doesn't exist return false
		if (project == null) {
			return false;
		}
		
		// if project not owned by sid then return false
		if (!project.getSupervisorId().equals(sid)) {
			return false;
		}
		
		project.setTitle(newTitle);
		return true;
	}
	
	/**
     * Updates the title of a project owned by the given student.
	 * @param pid the ID of the project to update
	 * @param studentid the ID of the student who owns the project
	 * @param newTitle the new title to set for the project
	 * @return true if the project's title was updated successfully, false otherwise
	 */
	public static boolean updateProjectTitleStudent(int pid, String studentid, String newTitle) {
		Project project = ProjectCollection.getInstance().getById(pid);
		
		// if project doesn't exist return false
		if (project == null) {
			return false;
		}
		
		// if project not under student then return false
		if (!project.getStudentId().equals(studentid)) return false;
		
		project.setTitle(newTitle);
		return true;
	}
}
