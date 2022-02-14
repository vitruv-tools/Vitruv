package tools.vitruv.framework.change.models;

/**
 * Listener for the change propagation status.
 */
public interface ChangePropagationListener {

	/**
	 * Called before the change propagation is started.
	 */
	void startedChangePropagation();

	/**
	 * Called after the change propagation is finished.
	 */
	void finishedChangePropagation();

	/**
	 * Called if the change propagation has been aborted.
	 *
	 * @param cause The cause for the abortion.
	 */
	void abortedChangePropagation(ChangePropagationAbortCause cause);

}
