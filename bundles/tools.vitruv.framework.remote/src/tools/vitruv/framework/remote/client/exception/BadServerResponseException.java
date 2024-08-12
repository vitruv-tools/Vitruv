package tools.vitruv.framework.remote.client.exception;

public class BadServerResponseException extends RuntimeException {
	private int statusCode = -1;
	
	public BadServerResponseException() {
		super();
	}
	
	public BadServerResponseException(String msg) {
		super(msg);
	}
	
	public BadServerResponseException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}
	
	public BadServerResponseException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BadServerResponseException(Throwable cause) {
		super(cause);
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
