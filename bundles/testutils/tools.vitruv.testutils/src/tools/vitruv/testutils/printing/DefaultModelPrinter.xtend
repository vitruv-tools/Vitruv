package tools.vitruv.testutils.printing

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import static tools.vitruv.testutils.printing.PrintResult.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import java.util.Collection
import static tools.vitruv.testutils.printing.PrintMode.*

class DefaultModelPrinter implements ModelPrinter {
	@Accessors
	var PrintIdProvider idProvider = new PrintIdProvider()

	override printObject(PrintTarget target, Object object) { dispatchPrintObject(target, object) }

	override printObjectShortened(PrintTarget target, Object object) { dispatchPrintObjectShortened(target, object) }

	def private dispatch PrintResult dispatchPrintObject(extension PrintTarget target, Resource resource) {
		print('Resource(') + newLineIncreaseIndent + //
		print('uri=') + print(resource.URI.toString) + print(',') + //
		newLine + print('content=') + printList(resource.contents, printModeFor(resource.contents)) [ subTarget, element |
			subTarget.printObject(element)
		] + newLineDecreaseIndent + //
		print(')')
	}

	def private dispatch PrintResult dispatchPrintObject(extension PrintTarget target, EClass eClass) {
		print(eClass.name)
	}

	def private dispatch PrintResult dispatchPrintObject(extension PrintTarget target, EObject object) {
		target.printObjectWithContent(object) [ contentTarget |
			contentTarget.printIterableElements(object.eClass.EAllStructuralFeatures) [ subTarget, feature |
				subTarget.printFeature(object, feature)
			]
		]
	}

	def private dispatch PrintResult dispatchPrintObject(extension PrintTarget target, Object object) {
		printValue(object)
	}

	def private dispatch dispatchPrintObject(extension PrintTarget target, Void void) {
		print('\u2205' /* empty set */ )
	}

	/**
	 * Called to print the provided {@code feature} of the provided {@code object}. Should use 
	 * {@link #printFeatureValue} to print values. 
	 */
	def protected PrintResult printFeature(extension PrintTarget target, EObject object, EStructuralFeature feature) {
		if (feature.derived) return PRINTED_NO_OUTPUT

		return print(feature.name) + print('=') + //
		if (feature.isMany) {
			if (feature.isOrdered) {
				target.printFeatureValueList(feature, object.eGet(feature) as Collection<?>)
			} else {
				target.printFeatureValueSet(feature, object.eGet(feature) as Collection<?>)
			}
		} else if (!object.eIsSet(feature)) {
			print('\u2205' /* empty set */ )
		} else {
			target.printFeatureValue(feature, object.eGet(feature))
		}
	}

	/**
	 * Determines the mode to print the provided {@code elements} with.
	 */
	def protected printModeFor(Collection<?> elements) {
		if (elements.size > 1) MULTI_LINE else SINGLE_LINE
	}

	/**
	 * Called when printing the list value of the provided {@code feature}. 
	 */
	def protected PrintResult printFeatureValueList(PrintTarget target, EStructuralFeature feature,
		Collection<?> valueList) {
		target.printList(valueList) [ subTarget, element |
			subTarget.printFeatureValue(feature, element)
		]
	}

	/**
	 * Called when printing the set value of the provided {@code feature}. 
	 */
	def protected PrintResult printFeatureValueSet(PrintTarget target, EStructuralFeature feature,
		Collection<?> valueSet) {
		target.printSet(valueSet) [ subTarget, element |
			subTarget.printFeatureValue(feature, element)
		]
	}

	/**
	 * Called when printing any value of the provided {@code feature}. 
	 */
	def protected PrintResult printFeatureValue(PrintTarget target, EStructuralFeature feature, Object value) {
		target.printObject(value)
	}

	def private dispatch dispatchPrintObjectShortened(extension PrintTarget target, Resource resource) {
		print('Resource(uri=') + print(resource.URI.toString) + print(', content=') + //
		target.printList(resource.contents) [ subTarget, element |
			subTarget.printObjectShortened(element)
		] + print(')')
	}

	def private dispatch dispatchPrintObjectShortened(PrintTarget target, EObject object) {
		target.printObjectWithContent(object) [ contentTarget |
			contentTarget.print('…')
		]
	}

	def private dispatch dispatchPrintObjectShortened(extension PrintTarget target, Object object) {
		print(object.class.simpleName) + print('(…)')
	}

	def private dispatch dispatchPrintObjectShortened(extension PrintTarget target, Void void) {
		print('\u2205' /* empty set */ )
	}

	/**
	 * Helper to print an {@link EObject}. Will print the object’s ID and, if the object has not been printed yet,
	 * use {@link contentPrinter} to print the object’s content.
	 */
	def protected <T extends EObject> printObjectWithContent(PrintTarget target, T object,
		(PrintTarget)=>PrintResult contentPrinter) {
		idProvider.ifAlreadyHasIdElse(object, [target.printAlreadyPrinted(it)]) [ objectId |
			target.print(objectId) + target.print('(') + contentPrinter.apply(target) + target.print(')')
		]
	}

	/**
	 * Called when printing an {@link EObject} that has previously been printed.
	 */
	def protected printAlreadyPrinted(extension PrintTarget target, String id) {
		print(id)
	}

	/**
	 * Delegates to {@link PrintTarget#printIterable} after consulting {@link #printModeFor}.
	 */
	def protected <T> PrintResult printIterable(PrintTarget target, String start, String end,
		Collection<? extends T> elements, (PrintTarget, T)=>PrintResult elementPrinter) {
		target.printIterable(start, end, elements, printModeFor(elements), elementPrinter)
	}

	/**
	 * Delegates to {@link PrintTarget#printList} after consulting {@link #printModeFor}.
	 */
	def protected <T> PrintResult printList(PrintTarget target, Collection<? extends T> elements,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		target.printList(elements, printModeFor(elements), elementPrinter)
	}

	/**
	 * Delegates to {@link PrintTarget#printSet} after consulting {@link #printModeFor}.
	 */
	def protected <T> PrintResult printSet(PrintTarget target, Collection<? extends T> elements,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		target.printSet(elements, printModeFor(elements), elementPrinter)
	}

	/**
	 * Delegates to {@link PrintTarget#printIterableElements} after consulting {@link #printModeFor}.
	 */
	def protected <T> PrintResult printIterableElements(PrintTarget target, Collection<? extends T> elements,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		target.printIterableElements(elements, printModeFor(elements), elementPrinter)
	}
}
