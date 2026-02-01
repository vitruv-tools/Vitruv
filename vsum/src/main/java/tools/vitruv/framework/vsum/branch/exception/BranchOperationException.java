package tools.vitruv.framework.vsum.branch.exception;

import tools.vitruv.framework.vsum.branch.BranchManager;

/**
 * Thrown when a branch operation in Vitruvius's branching system fails.
 * This includes failures during branch creation, switching, deletion, or any operations managed by {@link BranchManager}.
 */
public class BranchOperationException extends Exception {

    /**
     * Creates a new {@link BranchOperationException} with the given message.
     * @param message a description of the failure
     */
    public BranchOperationException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link BranchOperationException} with the given message and cause.
     * @param message a description of the failure
     * @param cause the underlying cause of the failure
     */
    public BranchOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
