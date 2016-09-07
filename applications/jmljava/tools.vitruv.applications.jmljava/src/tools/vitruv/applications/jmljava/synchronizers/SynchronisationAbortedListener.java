package tools.vitruv.applications.jmljava.synchronizers;

import tools.vitruv.framework.change.description.GeneralChange;
import tools.vitruv.framework.modelsynchronization.TransformationAbortCause;

/**
 * Listener for aborted synchronisations.
 * 
 * @author Stephan Seifermann
 *
 */
public interface SynchronisationAbortedListener {

    /**
     * Called whenever a transformation has been aborted.
     * 
     * @param abortedChange
     *            The change, which triggered the aborted transformation.
     */
    void synchronisationAborted(GeneralChange abortedChange);
    
    /**
     * Called whenever a transformation has been aborted.
     * 
     * @param cause
     *            The cause for the abortion.
     */
    void synchronisationAborted(TransformationAbortCause cause);

}
