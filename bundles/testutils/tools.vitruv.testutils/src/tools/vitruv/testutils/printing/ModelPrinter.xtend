package tools.vitruv.testutils.printing

/**
 * Strategy determining how to print model objects.
 */
interface ModelPrinter {
	/**
	 * Makes this printer use the given {@code provider} to determine object IDs.
	 */
	def void setIdProvider(PrintIdProvider provider)

	/**
	 * Prints the provided {@code object}.
	 * 
	 * @throws RuntimeException if {@code object} could not be printed successfully. 
	 */
	def PrintResult printObject(PrintTarget target, Object object)
	
	/**
	 * Prints the provided {@code object} shortened, i.e. in a manner that conveys only an overview of the object.
	 */
	def PrintResult printObjectShortened(PrintTarget target, Object object)
}
