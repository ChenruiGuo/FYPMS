package entities;

public interface IPassword {
	
	/**
	 * get user password
	 * @return password
	 */
	public String getPassword();
	
	/*
	 * set user password
	 * @param password
	 */
	public void setPassword(String password);
	
	
	/**
	 * validate the input password
	 * @param inputPass
	 * @return password hit or miss
	 */
	public boolean validatePass(String inputPass);
	
}
