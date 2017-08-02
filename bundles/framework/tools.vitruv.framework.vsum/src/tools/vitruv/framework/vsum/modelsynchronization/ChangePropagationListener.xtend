package tools.vitruv.framework.vsum.modelsynchronization

/** 
 * Listener for the change propagation status.
 */
interface ChangePropagationListener {
	/** 
	 * Called before the change propagation is started.
	 */
	def void startedChangePropagation()

	/** 
	 * Called after the change propagation is finished.
	 */
	def void finishedChangePropagation()

	/** 
	 * Called if the change propagation has been aborted.
	 * @param causeThe cause for the abortion.
	 */
	def void abortedChangePropagation(ChangePropagationAbortCause cause)

}
