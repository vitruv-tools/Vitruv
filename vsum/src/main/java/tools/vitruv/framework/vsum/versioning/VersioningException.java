package tools.vitruv.framework.vsum.versioning;

/**
 * Thrown when a versioning operation in VersioningService fails.
 */
public class VersioningException extends Exception {

    public VersioningException(String message) {
        super(message);
    }

    public VersioningException(String message, Throwable cause) {
        super(message, cause);
    }
}