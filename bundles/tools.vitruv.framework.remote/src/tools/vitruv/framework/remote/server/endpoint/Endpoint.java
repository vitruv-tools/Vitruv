package tools.vitruv.remote.server.endpoint;

import spark.Request;
import spark.Response;
import tools.vitruv.remote.server.exception.ServerHaltingException;

/**
 * Represents an endpoint of a REST interface.
 */
public abstract class Endpoint {
	
	/**
	 * The path name of this endpoint
	 */
	protected String path;
	
	/**
	 * The accept-type of this endpoint
	 */
	protected String acceptType;
	
	public Endpoint(String path, String acceptType) {
		this.path = path;
		this.acceptType = acceptType;
	}
	
	public Endpoint(String path) {
		this(path, "*/*");
	}
	
	/**
	 * Initializes this endpoint.
	 */
	public abstract void init();
	
	/**
	 * Handles the request, when this end point is called.
	 * 
	 * @param request the request object
	 * @param response the response object
	 * @return the response of this end point.
	 */
	public abstract Object handleRequest(Request request, Response response);
	
	/**
	 * Halts the execution of the requested endpoint and returns the status code NOT FOUND with the given message.
	 * 
	 * @param msg A message containing the reason of halting the execution.
	 */
	protected void notFound(String msg) {
		throw new ServerHaltingException(404, msg);
	}
	
	/**
	 * Halts the execution of the requested endpoint and returns the status code INTERNAL SERVER ERROR with the given message.
	 * 
	 * @param msg A message containing the reason of halting the execution.
	 */
	protected void internalServerError(String msg) {
		throw new ServerHaltingException(500, msg);
	}
}
