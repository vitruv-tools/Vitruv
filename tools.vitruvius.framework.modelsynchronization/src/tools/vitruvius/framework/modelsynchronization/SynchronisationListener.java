package tools.vitruvius.framework.modelsynchronization;

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
     * @param cause
     *            The cause for the abortion.
     */
    void syncAborted(TransformationAbortCause cause);

}
