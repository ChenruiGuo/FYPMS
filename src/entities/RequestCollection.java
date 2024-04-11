package entities;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * A collection class for Request objects.
 */
public class RequestCollection implements IItemCollection<Request> {
	
	private static int numRequests = 0;
	private static RequestCollection instance = null;
	protected ArrayList<Request> requests;
	
	private RequestCollection() {
		requests = new ArrayList<Request>();
	}
	
	/**
	 * Returns the singleton instance of RequestCollection.
	 * @return the singleton instance of RequestCollection
	 */
	public static synchronized RequestCollection getInstance() {
		if (instance == null) 
			instance = new RequestCollection();
		return instance;
	}
	
	/**
	 * Filters the requests based on the given predicate.
	 * @param predicate the predicate to filter by
	 * @return an ArrayList of Request objects that satisfy the given predicate
	 */

	public ArrayList<Request> filter(Predicate<Request> predicate) {
		ArrayList<Request> filteredList = new ArrayList<>();
		for (Request request : this.requests) {
			if (predicate.test(request)) {
				filteredList.add(request);
			}
		}
		
		return filteredList;
	}
	
	/**
	 * Inserts a new request into the collection.
	 * @param request the request to insert
	 */
	public void insert(Request request) {
		request.setRequestID(numRequests + 1);
		requests.add(request);
		numRequests++;
	}
	
	/**
	 * Returns all requests in the collection.
	 * @return an ArrayList of all Request objects in the collection
	 */
	public ArrayList<Request> getAll() {
		ArrayList<Request> returnRequests = new ArrayList<Request>();
		for (Request request : this.requests) {
			returnRequests.add(request);
		}
		return returnRequests;
	}
	
	/**
	 * Returns the request with the given ID.
	 * @param requestID the ID of the request to retrieve
	 * @return the Request object with the given ID, or null if not found
	 */
	public Request getById(int requestID) {
		for (Request request : requests)
			if (request.getRequestID() == requestID) return request;
		return null;
	}	
}
