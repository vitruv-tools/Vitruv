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
import org.eclipse.emf.common.util.URI
import static tools.vitruv.testutils.printing.PrintMode.multiLineIfAtLeast
import org.eclipse.emf.ecore.EReference
import java.util.List

@FinalFieldsConstructor
final class DefaultModelPrinter implements ModelPrinter {
	val ModelPrinter subPrinter
	static val ITERABLE_PRINT_MODE = multiLineIfAtLeast(2)

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
		val eClass = object.eClass
		val idAttribute = eClass.EIDAttribute
		val featuresToPrint = (if (idAttribute !== null) List.of(idAttribute) else emptyList)
			+ eClass.EAllAttributes.filter [it != idAttribute]
		 	+ eClass.EAllReferences
		 	
		print(eClass.name) 
			+ (if (idAttribute === null) print('#') + print(idProvider.getFallbackId(object)) else PRINTED_NO_OUTPUT)
			+ printIterable('(', ')', featuresToPrint, ITERABLE_PRINT_MODE) [ subTarget, feature |
				subPrinter.printFeature(subTarget, idProvider, object, feature)
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

	def protected getPrintMode() { multiLineIfAtLeast(2) }

	override printFeatureValueList(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Collection<?> valueList
	) {
		target.printList(valueList, ITERABLE_PRINT_MODE) [ subTarget, element |
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
		target.printSet(valueSet, ITERABLE_PRINT_MODE) [ subTarget, element |
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
			EObject: {
				switch (feature) {
					EReference case feature.isContainment: subPrinter.printObject(target, idProvider, value)
					default: subPrinter.printObjectShortened(target, idProvider, value)
				}
			}
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
		extension PrintTarget target,
		PrintIdProvider idProvider,
		EObject object
	) {
		val idAttribute = object.eClass.EIDAttribute
		print(object.eClass.name)
			+ if (idAttribute !== null) {
			 	print('(') + printFeature(target, idProvider, object, idAttribute) + print(')')
			} else {
				print('#') + print(idProvider.getFallbackId(object))
			}
	}

	def private dispatch dispatchPrintObjectShortened(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Object object
	) {
		print(object.class.simpleName) + print('#') + print(idProvider.getFallbackId(object))
	}

	def private dispatch dispatchPrintObjectShortened(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Void void
	) {
		print('\u2205' /* empty set */ )
	}

	override withSubPrinter(ModelPrinter printer) {
		new DefaultModelPrinter(printer)
	}
}
