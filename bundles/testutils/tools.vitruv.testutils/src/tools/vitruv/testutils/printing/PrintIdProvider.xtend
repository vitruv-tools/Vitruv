package tools.vitruv.testutils.printing

import org.eclipse.emf.ecore.EObject

interface PrintIdProvider {
	/**
	 * Uses {@code existingPrinter} to print {@code object} if {@code object} was already printed, and 
	 * {@code newPrinter} if {@code object} was not printed yet.
	 * 
	 * @param existingPrinter a function receiving an object and its identifier and printing the object.
	 * @param newPrinter a function receiving an object and its identifier and printing the object.
	 */
	def <T extends EObject> PrintResult ifAlreadyPrintedElse(T object, (T, String)=>PrintResult existingPrinter,
		(T, String)=>PrintResult newPrinter)

	/**
	 * Like {@link #ifAlreadyPrintedElse}, but uses {@code printer} in both cases.
	 * 	 
	 * @param printer a function receiving an object and its identifier and printing the object.
	 */
	def <T extends EObject> PrintResult printWithId(T object, (T, String)=>PrintResult printer) {
		ifAlreadyPrintedElse(object, printer, printer)
	}
}
