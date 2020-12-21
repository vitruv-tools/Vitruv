package tools.vitruv.testutils.printing

/**
 * Strategy determining how to print model objects.
 */
interface ModelPrinter {
	/**
	 * Creates a copy of this printer that delegates to {@code subPrinter} whenever printing a subpart of an object. 
	 * This allows other printers to change the printing logic for objects referenced by an object that is printed
	 * by this printer.
	 */
	def ModelPrinter withSubPrinter(ModelPrinter subPrinter)

	/**
	 * Prints the provided {@code object}.
	 * 
	 * @throws RuntimeException if {@code object} could not be printed successfully. 
	 */
	def PrintResult printObject(PrintTarget target, PrintIdProvider idProvider, Object object)

	/**
	 * Prints the provided {@code object} shortened, i.e. in a manner that conveys only an overview of the object.
	 */
	def PrintResult printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object)
}
