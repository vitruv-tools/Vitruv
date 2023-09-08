package tools.vitruv.framework.remote.client.exception;

public class BadServerResponseException extends RuntimeException {

	public BadServerResponseException() {
		super();
	}
	
	public BadServerResponseException(String msg) {
		super(msg);
	}
	
	public BadServerResponseException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BadServerResponseException(Throwable cause) {
		super(cause);
	}
}
