package tools.vitruv.testutils.printing

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.resource.Resource
import static tools.vitruv.testutils.printing.PrintResult.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*

class DefaultModelPrinter implements ModelPrinter {
	@Accessors
	var PrintIdProvider idProvider = new PrintIdProvider()

	override printObject(PrintTarget target, Object object) { dispatchPrintObject(target, object) }

	def private dispatch PrintResult dispatchPrintObject(extension PrintTarget target, Resource resource) {
		print('Resource(uri=') + print(resource.URI.toString) + print(', content=') + printList(resource.contents) [ subTarget, element |
			subTarget.printObject(element)
		] + print(')')
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

	def protected PrintResult printFeature(extension PrintTarget target, EObject object, EStructuralFeature feature) {
		if (feature.derived) return PRINTED_NO_OUTPUT

		return print(feature.name) + print('=') + //
		if (feature.isMany) {
			if (feature.isOrdered) {
				printList(object.eGet(feature) as List<?>) [ subTarget, element |
					subTarget.printFeatureValue(feature, element)
				]
			} else {
				printSet(object.eGet(feature) as Set<?>) [ subTarget, element |
					subTarget.printFeatureValue(feature, element)
				]
			}
		} else if (!object.eIsSet(feature)) {
			print('\u2205' /* empty set */ )
		} else {
			target.printFeatureValue(feature, object.eGet(feature))
		}
	}

	def protected PrintResult printFeatureValue(PrintTarget target, EStructuralFeature feature, Object value) {
		target.printObject(value)
	}

	def protected printShortened(PrintTarget target, EObject object) {
		target.printObjectWithContent(object) [ contentTarget |
			contentTarget.print('…')
		]
	}

	def protected <T extends EObject> printObjectWithContent(PrintTarget target, T object,
		(PrintTarget)=>PrintResult contentPrinter) {
		idProvider.ifAlreadyHasIdElse(object, [target.printAlreadyPrinted(it)]) [ objectId |
			target.print(objectId) + target.print('(') + contentPrinter.apply(target) + target.print(')')
		]
	}

	def protected printShortened(extension PrintTarget target, Object object) {
		print(object.class.simpleName) + print('(…)')
	}

	def protected printAlreadyPrinted(extension PrintTarget target, String id) {
		print(id)
	}
}
