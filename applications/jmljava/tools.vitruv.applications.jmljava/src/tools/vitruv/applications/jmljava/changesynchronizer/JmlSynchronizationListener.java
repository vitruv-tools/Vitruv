package tools.vitruv.applications.jmljava.changesynchronizer;

import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.modelsynchronization.SynchronisationListener;

public interface JmlSynchronizationListener extends SynchronisationListener {

	/**
     * Called if the synchronisation has been aborted.
     *
     * @param abortedChange
     *            The unprocessed change because of the aborted transformation.
     */
    void syncAborted(TransactionalChange abortedChange);
	
}
