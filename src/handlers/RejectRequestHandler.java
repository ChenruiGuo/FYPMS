package handlers;

import entities.Request;

/**
 * A handler that rejects a request.
 */
public class RejectRequestHandler extends Handler {
	
	/**
	 * Handles a request by rejecting it.
	 *
	 * @param request The request to be handled.
	 */
	@Override
	public void handleRequest(Request request) {
		request.setStatus(Request.RequestStatus.REJECTED);
	}

}
