package handlers;

import entities.Request;

/**
 * A handler that approves a request.
 */
public class ApproveRequestHandler extends Handler {

	/**
	 * Handles a request by approving it.
	 *
	 * @param request The request to be handled.
	 */
	@Override
	public void handleRequest(Request request) {
		request.setStatus(Request.RequestStatus.APPROVED);
	}

}
