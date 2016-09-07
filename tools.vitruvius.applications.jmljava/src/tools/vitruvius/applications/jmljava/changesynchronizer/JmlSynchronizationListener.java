package tools.vitruvius.applications.jmljava.changesynchronizer;

import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.modelsynchronization.SynchronisationListener;

public interface JmlSynchronizationListener extends SynchronisationListener {

	/**
     * Called if the synchronisation has been aborted.
     *
     * @param abortedChange
     *            The unprocessed change because of the aborted transformation.
     */
    void syncAborted(GeneralChange abortedChange);
	
}
