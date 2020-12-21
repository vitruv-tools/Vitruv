package tools.vitruv.testutils.printing

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import static tools.vitruv.testutils.printing.PrintResult.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import java.util.Collection
import static tools.vitruv.testutils.printing.PrintMode.*
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.Accessors
import static com.google.common.base.Preconditions.checkState

@FinalFieldsConstructor
class DefaultModelPrinter implements ModelPrinter {
	@Accessors(PROTECTED_GETTER)
	val ModelPrinter subPrinter

	new() {
		subPrinter = this
	}

	override printObject(PrintTarget target, PrintIdProvider idProvider, Object object) {
		dispatchPrintObject(target, idProvider, object)
	}

	override printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object) {
		dispatchPrintObjectShortened(target, idProvider, object)
	}

	def private dispatch PrintResult dispatchPrintObject(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Resource resource
	) {
		print('Resource(') + newLineIncreaseIndent + //
		print('uri=') + target.printResourceUri(resource.URI) + print(',') + //
		newLine + print('content=') + printList(resource.contents, printModeFor(resource.contents)) [ subTarget, element |
			subPrinter.printObject(subTarget, idProvider, element)
		] + newLineDecreaseIndent + //
		print(')')
	}

	def private dispatch PrintResult dispatchPrintObject(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		EClass eClass
	) {
		print(eClass.name)
	}

	def private dispatch PrintResult dispatchPrintObject(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		EObject object
	) {
		target.printObjectWithContent(idProvider, object) [ contentTarget |
			contentTarget.printIterableElements(object.eClass.EAllStructuralFeatures) [ subTarget, feature |
				subTarget.printFeature(idProvider, object, feature)
			]
		]
	}

	def private dispatch dispatchPrintObject(extension PrintTarget target, PrintIdProvider idProvider, Object object) {
		printValue(object)
	}

	def private dispatch dispatchPrintObject(extension PrintTarget target, PrintIdProvider idProvider, Void void) {
		print('\u2205' /* empty set */ )
	}

	/**
	 * Called to print the provided {@code feature} of the provided {@code object}. Should use 
	 * {@link #printFeatureValue} to print values. 
	 */
	def protected PrintResult printFeature(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature
	) {
		if (feature.derived) return PRINTED_NO_OUTPUT

		return print(feature.name) + print('=') + //
		if (feature.isMany) {
			if (feature.isOrdered) {
				target.printFeatureValueList(idProvider, feature, object.eGet(feature) as Collection<?>)
			} else {
				target.printFeatureValueSet(idProvider, feature, object.eGet(feature) as Collection<?>)
			}
		} else if (!object.eIsSet(feature)) {
			print('\u2205' /* empty set */ )
		} else {
			target.printFeatureValue(idProvider, feature, object.eGet(feature))
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
	def protected printFeatureValueList(
		PrintTarget target,
		PrintIdProvider idProvider,
		EStructuralFeature feature,
		Collection<?> valueList
	) {
		target.printList(valueList) [ subTarget, element |
			subTarget.printFeatureValue(idProvider, feature, element)
		]
	}

	/**
	 * Called when printing the set value of the provided {@code feature}. 
	 */
	def protected printFeatureValueSet(
		PrintTarget target,
		PrintIdProvider idProvider,
		EStructuralFeature feature,
		Collection<?> valueSet
	) {
		target.printSet(valueSet) [ subTarget, element |
			subTarget.printFeatureValue(idProvider, feature, element)
		]
	}

	/**
	 * Called when printing any value of the provided {@code feature}. 
	 */
	def protected PrintResult printFeatureValue(
		PrintTarget target,
		PrintIdProvider idProvider,
		EStructuralFeature feature,
		Object value
	) {
		subPrinter.printObject(target, idProvider, value)
	}

	/**
	 * Called when printing a resource’s URI (a Resource is not an {@link EObject} its features will not be printed
	 * via {@link #printFeatureValue}.
	 */
	def protected PrintResult printResourceUri(extension PrintTarget target, URI uri) {
		print(uri.toString)
	}

	def private dispatch dispatchPrintObjectShortened(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Resource resource
	) {
		print('Resource(uri=') + target.printResourceUri(resource.URI) + print(', content=') + //
		target.printList(resource.contents) [ subTarget, element |
			subPrinter.printObjectShortened(subTarget, idProvider, element)
		] + print(')')
	}

	def private dispatch dispatchPrintObjectShortened(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object
	) {
		target.printObjectWithContent(idProvider, object) [ contentTarget |
			contentTarget.print('…')
		]
	}

	def private dispatch dispatchPrintObjectShortened(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Object object
	) {
		print(object.class.simpleName) + print('(…)')
	}

	def private dispatch dispatchPrintObjectShortened(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Void void
	) {
		print('\u2205' /* empty set */ )
	}

	/**
	 * Helper to print an {@link EObject}. Will print the object’s ID and, if the object has not been printed yet,
	 * use {@link contentPrinter} to print the object’s content.
	 */
	def protected <T extends EObject> printObjectWithContent(PrintTarget target, PrintIdProvider idProvider, T object,
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

	override withSubPrinter(ModelPrinter printer) {
		checkState(
			this.class == DefaultModelPrinter,
			'''«this.class.simpleName» extends «DefaultModelPrinter.simpleName» without overriding withSubPrinter. «
			»This is an error because it effectively eliminiates the extending class when a sub printer is set.'''
		)
		new DefaultModelPrinter(printer)
	}
}
