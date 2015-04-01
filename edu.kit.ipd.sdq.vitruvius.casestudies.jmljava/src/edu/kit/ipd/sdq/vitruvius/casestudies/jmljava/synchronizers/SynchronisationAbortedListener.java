package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause;

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
    void synchronisationAborted(EMFModelChange abortedChange);
    
    /**
     * Called whenever a transformation has been aborted.
     * 
     * @param cause
     *            The cause for the abortion.
     */
    void synchronisationAborted(TransformationAbortCause cause);

}
