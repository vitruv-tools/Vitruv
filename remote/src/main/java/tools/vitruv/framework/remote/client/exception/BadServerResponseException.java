package tools.vitruv.framework.remote.client.exception;

public class BadServerResponseException extends RuntimeException {
	private final int statusCode;
	
	public BadServerResponseException() {
		super();
    this.statusCode = -1;
	}
	
	public BadServerResponseException(String msg) {
		super(msg);
    this.statusCode = -1;
	}
	
	public BadServerResponseException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}
	
	public BadServerResponseException(String msg, Throwable cause) {
		super(msg, cause);
    this.statusCode = -1;
	}
	
	public BadServerResponseException(Throwable cause) {
		super(cause);
    this.statusCode = -1;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
