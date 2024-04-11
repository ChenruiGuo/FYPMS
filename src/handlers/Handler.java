package handlers;

import entities.Request;
import exceptions.HandlerFailedException;

/**
 * An abstract class that defines a request handler.
 */
public abstract class Handler {
    protected Handler next;

    /**
     * Sets the next handler in the chain of responsibility.
     *
     * @param next The next handler.
     */
    public void setNext(Handler next) {
        this.next = next;
    }

    /**
     * Handles a request. If the request cannot be handled by this handler, it is passed on to the next handler in the chain.
     *
     * @param request The request to be handled.
     * @throws HandlerFailedException If the handler fails to handle the request.
     */
    public abstract void handleRequest(Request request) throws HandlerFailedException;
}
