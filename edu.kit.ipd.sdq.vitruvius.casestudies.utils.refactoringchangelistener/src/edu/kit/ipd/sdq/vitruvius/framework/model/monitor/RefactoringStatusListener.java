package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

/**
 * @author messinger
 * 
 *         Is informed when a refactoring is about to be executed and after it has been executed.
 */
public interface RefactoringStatusListener {

    public void postExecute();

    public void preExecute();

    public enum RefactoringStatus {
        PRE_EXECUTE, POST_EXECUTE
    }
}
