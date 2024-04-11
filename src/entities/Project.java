package entities;

/**
* Information related to FYP projects
*/

public class Project {
    private String supervisorId;
    private String title;
    private int projectId;
    private String studentId;
    private ProjectStatus status;
    /*
     * Possible Project status
     * AVAILABLE: can be taken by student
     * UNAVAILABLE: cannot be taken by any student
     * RESERVED: pending request
     * ALLOCATED: student has taken this project
     * */
    
    public static enum ProjectStatus {
    	AVAILABLE,
    	UNAVAILABLE,
    	RESERVED,
    	ALLOCATED
    }
 

    public Project(String title, String supervisorId, String studentId, ProjectStatus status) {
    	this.title = title;
        this.supervisorId = supervisorId;
        this.studentId = studentId;
        this.status = status;
    }

    /**
    * get supervisor ID
    * @return supervisor ID
    */
    public String getSupervisorId() { return this.supervisorId; }
    /**
    * set supervisor ID
    * @param supervisor ID
    */
    public void setSupervisorId(String id) { this.supervisorId = id; }
    
    /**
    * get project title
    * @return project title
    */
    public String getTitle() { return this.title; }
    
    /**
    * set project title
    * @param project title
    */
    public void setTitle(String title) { this.title = title; }
    
    /**
    * get project ID
    * @return project ID
    */
    public int getProjectId() { return this.projectId; }
    /**
    * set project ID
    * @param project ID
    */
    public void setProjectId(int pid) { this.projectId = pid; }
    
    /**
    * get student ID
    * @return student ID
    */
    public String getStudentId() { return this.studentId; }
    /**
    * set student ID
    * @param student ID
    */
    public void setStudentId(String id) { this.studentId = id; }
    
    /**
    * get project status
    * @return project status
    */
    public ProjectStatus getStatus() { return this.status; }
    /**
    * set project status
    * @param project status
    */
    public void setStatus(ProjectStatus status) { this.status = status; }
    
    
}
