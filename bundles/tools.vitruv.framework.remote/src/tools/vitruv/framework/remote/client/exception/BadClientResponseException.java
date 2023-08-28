package tools.vitruv.framework.remote.client.exception;

public class BadClientResponseException extends RuntimeException {

	public BadClientResponseException() {
		super();
	}
	
	public BadClientResponseException(String msg) {
		super(msg);
	}
	
	public BadClientResponseException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BadClientResponseException(Throwable cause) {
		super(cause);
	}
}
