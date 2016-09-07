package tools.vitruvius.domains.java.monitorededitor.refactoringlistener;

/**
 * @author messinger
 * 
 *         Is informed when a refactoring is about to be executed and after it has been executed.
 */
public interface RefactoringStatusListener {

    public void postExecute();

    public void preExecute();
    
    public void aboutPostExecute();

    public enum RefactoringStatus {
        PRE_EXECUTE, ABOUT_POST_EXECUTE, POST_EXECUTE
    }
}
