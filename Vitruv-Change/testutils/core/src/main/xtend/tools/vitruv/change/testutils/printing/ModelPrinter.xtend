package tools.vitruv.change.testutils.printing

import static tools.vitruv.change.testutils.printing.PrintResult.NOT_RESPONSIBLE
import org.eclipse.emf.ecore.EObject
import java.util.Collection
import org.eclipse.emf.ecore.EStructuralFeature

/**
 * Strategy determining how to print model objects.
 * <p>
 * Implementation note: Model printers are expected to be immutable. Furthermore, they should delegate to a sub printer
 * whenever they do not feel responsible for printing some value. This allows other printers to modify the behaviour
 * if they wish. Model printers get the sub printer they should use handed in through the method [#withSubPrinter].
 */
interface ModelPrinter {
	/**
	 * Creates a copy of this printer that delegates to {@code subPrinter} whenever printing a subpart of an object. 
	 * This allows other printers to change the printing logic for objects referenced by an object that is printed
	 * by this printer.
	 * <p>
	 * Implementation note: Because model printers are expected to be immutable, implementations should either return
	 * {@code this} (if they don’t need a sub printer) or create a new instance with the {@code subPrinter}.
	 */
	def ModelPrinter withSubPrinter(ModelPrinter subPrinter)

	/**
	 * Prints the provided {@code object}.
	 * 
	 * @throws RuntimeException if {@code object} could not be printed successfully. 
	 */
	def PrintResult printObject(PrintTarget target, PrintIdProvider idProvider, Object object) {
		NOT_RESPONSIBLE
	}

	/**
	 * Prints the provided {@code object} shortened, i.e. in a manner that conveys only an overview of the object.
	 */
	def PrintResult printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object) {
		NOT_RESPONSIBLE
	}

	/**
	 * Print the provided {@code feature} of the provided {@code object}. Includes an identifier for the feature and
	 * the feature value in the output.
	 */
	def PrintResult printFeature(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature
	) {
		NOT_RESPONSIBLE
	}

	/**
	 * Prints the list value of the provided {@code feature}. 
	 */
	def printFeatureValueList(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Collection<?> valueList
	) {
		NOT_RESPONSIBLE
	}

	/**
	 * Prints the set value of the provided {@code feature}. 
	 */
	def printFeatureValueSet(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Collection<?> valueSet
	) {
		NOT_RESPONSIBLE
	}

	/**
	 * Prints one of the values of the provided {@code feature}. 
	 */
	def PrintResult printFeatureValue(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Object value
	) {
		NOT_RESPONSIBLE
	}
}
