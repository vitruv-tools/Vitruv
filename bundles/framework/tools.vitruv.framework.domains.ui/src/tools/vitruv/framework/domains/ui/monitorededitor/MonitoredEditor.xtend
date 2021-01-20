package tools.vitruv.framework.domains.ui.monitorededitor

interface MonitoredEditor {
	/**
	 * Triggers the propagation of yet recorded changes.
	 * Does nothing if no changes were recorded.
	 */
	def void propagateRecordedChanges()

	/** 
	 * Sets the time after which change propagation is executed automatically if no further change
	 * occurs. Setting the value to -1 disables automatic propagation.
	 * @param millisecondsAfterLastChange the time after which change propagation is
	 * performed if no further change occurs
	 */
	def void setAutomaticPropagationAfterMilliseconds(int millisecondsAfterLastChange)
}
