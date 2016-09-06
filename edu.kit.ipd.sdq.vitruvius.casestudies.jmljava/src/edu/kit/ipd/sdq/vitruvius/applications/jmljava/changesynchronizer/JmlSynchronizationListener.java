package edu.kit.ipd.sdq.vitruvius.applications.jmljava.changesynchronizer;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.SynchronisationListener;

public interface JmlSynchronizationListener extends SynchronisationListener {

	/**
     * Called if the synchronisation has been aborted.
     *
     * @param abortedChange
     *            The unprocessed change because of the aborted transformation.
     */
    void syncAborted(GeneralChange abortedChange);
	
}
