package controllers;

import entities.SupervisorCollection;
import entities.User;
import entities.Supervisor;

/**
 * This class is responsible for controlling and managing supervisors.
 */
public class SupervisorController {
	
	/**
	 * Checks if a supervisor with the given ID exists.
	 * @param id The ID of the supervisor to check.
	 * @return true if the supervisor exists, false otherwise.
	 */
	public static boolean supervisorExists(String id) {
		User u = SupervisorCollection.getInstance().getUserById(id);
		if (u == null) return false;
		return true;
	}
	
	/**
	 * Gets the availability of the supervisor with the given ID.
	 * @param id The ID of the supervisor.
	 * @return true if the supervisor is available, false otherwise.
	 */
	public static boolean getSupervisorAvailabilityById(String id) {
		Supervisor sup = (Supervisor) SupervisorCollection.getInstance().getUserById(id);
		return sup.getIsAvailable();
	}
}
