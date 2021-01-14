package tools.vitruv.testutils.printing

import org.eclipse.emf.ecore.EObject

interface PrintIdProvider {
	/**
	 * Uses {@code existingPrinter} to print {@code object} if {@code object} was already printed, and 
	 * {@code newPrinter} if {@code object} was not printed yet.
	 * 
	 * @param existingPrinter a function receiving an identifier for {@code object} and printing {@code object}.
	 * @param newPrinter a function receiving an identifier for {@code object} and printing {@code object}.
	 */
	def PrintResult ifAlreadyPrintedElse(EObject object, (String)=>PrintResult existingPrinter,
		(String)=>PrintResult newPrinter)
}
