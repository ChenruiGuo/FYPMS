package entities;

/**
* user interface
*/

public interface IUser {
    /**
    * get user name
    * @return name
    */
    public String getName();
    /** 
    * get user email
    * @return email
    */
    public String getEmail();
	
    /**
    * get user id
    * @return user id 
    */
    public String getUserId();
	
    /**
    * set user id
    * @param user id
    */
    public void setUserId(String userId);
}
