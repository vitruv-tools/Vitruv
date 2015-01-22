package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.extensions;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.changesynchronizer.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;

/**
 * Listener for the synchronisation status.
 */
public interface SynchronisationListener {

    /**
     * Called before the synchronisation is started.
     */
    void syncStarted();

    /**
     * Called after the synchronisation is finished.
     */
    void syncFinished();

    /**
     * Called if the synchronisation has been aborted.
     * 
     * @param abortedChange
     *            The unprocessed change because of the aborted transformation.
     */
    void syncAborted(EMFModelChange abortedChange);

    /**
     * Called if the synchronisation has been aborted.
     * 
     * @param cause
     *            The cause for the abortion.
     */
    void syncAborted(TransformationAbortCause cause);

}
