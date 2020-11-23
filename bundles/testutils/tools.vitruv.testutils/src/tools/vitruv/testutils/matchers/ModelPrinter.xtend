package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import org.eclipse.emf.ecore.EClass
import org.hamcrest.Description
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.Objects
import java.util.function.Consumer
import java.util.List
import java.util.Set

@FinalFieldsConstructor
class ModelPrinter {
	val Description target
	val printed = new HashMap<EObject, String>()
	val classCount = new HashMap<EClass, Integer>()

	def private dispatch void print(Object object) {
		target.appendText(Objects.toString(object))
	}

	def private dispatch void print(EObject object) {
		var objectId = printed.get(object)
		if (objectId !== null) {
			target.appendText(objectId)
		} else {
			objectId = assignId(object)
			printed.put(object, objectId)
			val featuresToPrint = object.eClass.EAllStructuralFeatures.filter[!derived]
			target.appendText(objectId).appendText('(').appendCommaSeparated(featuresToPrint) [ feature |
				target.appendText(feature.name).appendText('=')
				if (!object.eIsSet(feature)) {
					target.appendText('<unset>')
				} else if (feature.isMany) {
					if (feature.isOrdered) {
						printList(object.eGet(feature) as List<? extends EObject>)
					} else {
						printSet(object.eGet(feature) as Set<? extends EObject>)
					}
				} else {
					print(object.eGet(feature))
				}
			].appendText(')')
		}
	}

	def private void printList(List<? extends EObject> objects) {
		target.appendText('[').appendCommaSeparated(objects)[print()].appendText(']')
	}

	def private void printSet(Set<? extends EObject> objects) {
		target.appendText('[').appendCommaSeparated(objects)[print()].appendText(']')
	}

	def private assignId(EObject object) {
		val index = classCount.compute(object.eClass) [ key, oldValue |
			if (oldValue === null) 1 else oldValue + 1
		]
		object.eClass.name + if (index == 1) "" else "#" + index
	}

	def private static <T> appendCommaSeparated(Description description, Iterable<? extends T> elements,
		Consumer<T> printer) {
		elements.forEach [ element, index |
			if (index !== 0) description.appendText(', ')
			printer.accept(element)
		]
		return description
	}

	def static appendEObjectValue(Description description, EObject object) {
		description.appendText('<')
		new ModelPrinter(description).print(object)
		description.appendText('>')
	}

	def static dispatch appendPrettyValue(Description description, EClass eClass) {
		description.appendText('''<«eClass.name»>''')
	}

	def static dispatch appendPrettyValue(Description description, EObject object) {
		description.appendEObjectValue(object)
	}

	def static dispatch appendPrettyValue(Description description, Object object) {
		description.appendValue(object)
	}

	def static dispatch appendPrettyValue(Description description, Void nul) {
		description.appendText('''<null>''')
	}

	def static appendPrettyValueList(Description description, List<? extends EObject> objects) {
		new ModelPrinter(description).printList(objects)
	}

	def static appendPrettyValueSet(Description description, List<? extends EObject> objects) {
		new ModelPrinter(description).printList(objects)
	}
}
