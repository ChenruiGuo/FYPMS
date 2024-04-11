package entities;

/** 
* Project details for requests
*/
public class ProjectRequest extends Request {
	private int projectId;
	private Project project;
	private Supervisor supervisor;
	private Student student;
	
	public ProjectRequest(String requestorID, int projectId) {
		super(requestorID);
		this.projectId = projectId;
	}
	
	/**
	* get the project ID
	* @return project ID
	*/
	public int getProjectId() {
		return this.projectId;
	}
	
	/**
	* get the project
	* @return project
	*/
	public Project getProject() {
		return project;
	}
	/**
	* set project
	* @param project
	*/
	public void setProject(Project project) {
		this.project = project;
	}
	
	/**
	* get the supervisor of the project
	* @return supervisor
	*/
	public Supervisor getSupervisor() {
		return supervisor;
	}
	/**
	* set supervisor
	* @param supervisor
	*/
	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	/**
	* get student assigned to project
	* @return student
	*/
	public Student getStudent() {
		return student;
	}
	/**
	* set student
	* @param student
	*/
	public void setStudent(Student student) {
		this.student = student;
	}


}
