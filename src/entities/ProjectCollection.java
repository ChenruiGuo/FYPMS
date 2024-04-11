package entities;

import java.util.ArrayList;
import java.util.function.Predicate;

import entities.Project.ProjectStatus;
/**
* collection of projects
*/
public class ProjectCollection implements IItemCollection<Project> {
	
	private static ProjectCollection instance = null;
	protected ArrayList<Project> projects;
	private static int maxFreeId = 1;
	
	
	private ProjectCollection() {
		projects = new ArrayList<Project>();
	}
	/**
	* get instance of project collection. If null, create new instance.
	* @return project collection instance
	*/
	public static synchronized ProjectCollection getInstance() {
		if (instance == null) 
			instance = new ProjectCollection();
		return instance;
	}
	
	
	// filtering predicates
	public static final Predicate<Project> P_AVAILABLE = project -> project.getStatus().equals(Project.ProjectStatus.AVAILABLE);
	
	/** 
	* filter projects
	* @return arraylist of projects
	* @param predicate
	*/
	public ArrayList<Project> filter(Predicate<Project> predicate) {
		ArrayList<Project> filteredList = new ArrayList<>();
		for (Project project : this.projects) {
			if (predicate.test(project)) {
				filteredList.add(project);
			}
		}
		
		return filteredList;
	}
	
	/**
	* insert new project
	* @param project
	*/
	public void insert(Project project) {
		// before adding project set project id
		project.setProjectId(maxFreeId);
		maxFreeId++;
		projects.add(project);
	}
	
	/**
	* get project by project id
	* @param project id
	* @return project
	*/
	public Project getById(int pid) {
		for (Project p : this.projects) 
			if (p != null && (p.getProjectId() == pid)) return p;

		return null;
	}
	
	/**
	* get all projects
	* @return arraylist of projects
	*/
	public ArrayList<Project> getAll() {
		ArrayList<Project> returnProjects = new ArrayList<Project>();
		for (Project project : this.projects) {
			returnProjects.add(project);
		}
		return returnProjects;
	}

}
