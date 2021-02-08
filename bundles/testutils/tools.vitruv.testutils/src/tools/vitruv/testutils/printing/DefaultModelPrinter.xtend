package tools.vitruv.testutils.printing

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import static tools.vitruv.testutils.printing.PrintResult.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import java.util.Collection
import static tools.vitruv.testutils.printing.PrintMode.*
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.Accessors
import static com.google.common.base.Preconditions.checkState
import org.eclipse.emf.common.util.URI

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
		print('Resource@') + target.printValue(resource.URI) [ subTarget, uri |
			subPrinter.printObject(subTarget, idProvider, uri)
		] + printList(resource.contents, MULTI_LINE) [ subTarget, element |
			subPrinter.printObject(subTarget, idProvider, element)
		]
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
		target.printObjectWithContent(idProvider, object) [ contentTarget, toPrint |
			contentTarget.printIterableElements(toPrint.eClass.EAllStructuralFeatures) [ subTarget, feature |
				subPrinter.printFeature(subTarget, idProvider, toPrint, feature)
			]
		]
	}

	def private dispatch dispatchPrintObject(extension PrintTarget target, PrintIdProvider idProvider, URI uri) {
		print(URI.decode(uri.toString()))
	}

	def private dispatch dispatchPrintObject(extension PrintTarget target, PrintIdProvider idProvider, Object object) {
		print(object.toString())
	}

	def private dispatch dispatchPrintObject(extension PrintTarget target, PrintIdProvider idProvider, Void void) {
		print('\u2205' /* empty set */ )
	}

	override PrintResult printFeature(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature
	) {
		if (feature.derived) return PRINTED_NO_OUTPUT

		return print(feature.name) + print('=') + //
		if (feature.isMany) {
			if (feature.isOrdered) {
				subPrinter.printFeatureValueList(target, idProvider, object, feature, object.eGet(feature) as Collection<?>)
			} else {
				subPrinter.printFeatureValueSet(target, idProvider, object, feature, object.eGet(feature) as Collection<?>)
			}
		} else {
			subPrinter.printFeatureValue(target, idProvider, object, feature, object.eGet(feature))
		}
	}

	/**
	 * Determines the mode to print the provided {@code elements} with.
	 */
	def protected printModeFor(Collection<?> elements) {
		if (elements.size > 1) MULTI_LINE else SINGLE_LINE
	}

	override printFeatureValueList(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Collection<?> valueList
	) {
		target.printList(valueList) [ subTarget, element |
			subPrinter.printFeatureValue(subTarget, idProvider, object, feature, element)
		]
	}

	override printFeatureValueSet(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Collection<?> valueSet
	) {
		target.printSet(valueSet) [ subTarget, element |
			subPrinter.printFeatureValue(subTarget, idProvider, object, feature, element)
		]
	}

	override PrintResult printFeatureValue(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Object value
	) {
		switch (value) {
			EObject: subPrinter.printObject(target, idProvider, value)
			default: target.printValue(value)[subTarget, theValue |
				subPrinter.printObject(subTarget, idProvider, theValue)
			]
		}
	}

	def private dispatch dispatchPrintObjectShortened(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Resource resource
	) {
		print('Resource@') + target.printValue(resource.URI) [ subTarget, uri |
			subPrinter.printObjectShortened(subTarget, idProvider, uri)
		] + printList(resource.contents, SINGLE_LINE) [ subTarget, element |
			subPrinter.printObjectShortened(subTarget, idProvider, element)
		]
	}

	def private dispatch dispatchPrintObjectShortened(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object
	) {
		target.printObjectWithContent(idProvider, object) [ contentTarget, _ |
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
		(PrintTarget, T)=>PrintResult contentPrinter) {
		idProvider.ifAlreadyPrintedElse(object, [obj, id|target.printAlreadyPrinted(obj, id)]) [ toPrint, id |
			target.print(id) + target.print('(') + contentPrinter.apply(target, toPrint) + target.print(')')
		]
	}

	/**
	 * Called when printing an {@link EObject} that has previously been printed.
	 */
	def protected printAlreadyPrinted(extension PrintTarget target, EObject object, String id) {
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
