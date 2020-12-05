package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import org.eclipse.emf.ecore.EClass
import org.hamcrest.Description
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.function.Consumer
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.resource.Resource

@FinalFieldsConstructor
class ModelPrinter {
	val Description target
	val printed = new HashMap<EObject, String>()
	val classCount = new HashMap<EClass, Integer>()

	def private dispatch void print(EObject object) {
		var objectId = printed.get(object)
		if (objectId !== null) {
			target.appendText(objectId)
		} else {
			objectId = assignId(object)
			printed.put(object, objectId)
			val featuresToPrint = object.eClass.EAllStructuralFeatures.filter[!derived]
			target.appendText(objectId).appendText('(').commaSeparated(featuresToPrint) [ feature |
				target.appendText(feature.name).appendText('=')
				if (!object.eIsSet(feature)) {
					target.appendText('\u2205' /* empty set */ )
				} else if (feature.isMany) {
					if (feature.isOrdered) {
						printList(object.eGet(feature) as List<?>)
					} else {
						printSet(object.eGet(feature) as Set<?>)
					}
				} else {
					print(object.eGet(feature))
				}
			].appendText(')')
		}
	}

	def private dispatch void print(Object object) {
		target.appendValue(object)
	}

	def private void printList(List<?> objects) {
		target.appendText('[').commaSeparated(objects)[print()].appendText(']')
	}

	def private void printSet(Set<?> objects) {
		target.appendText('{').commaSeparated(objects)[print()].appendText('}')
	}

	def private assignId(EObject object) {
		val index = classCount.compute(object.eClass) [ key, oldValue |
			if (oldValue === null) 1 else oldValue + 1
		]
		object.eClass.name + if (index == 1) "" else "#" + index
	}

	def private static <T> commaSeparated(Description description, Iterable<? extends T> elements,
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

	def static appendResourceValue(Description description, Resource resource) {
		description.appendText('<Resource(uri=').appendText(resource.URI.toString).appendText(', content=[').
			commaSeparated(resource.contents)[description.appendText('''«eClass.name»(…)''')].appendText('])>')
	}

	def static dispatch appendPrettyValue(Description description, EClass eClass) {
		description.appendText('''<«eClass.name»>''')
	}

	def static dispatch appendPrettyValue(Description description, EObject object) {
		description.appendEObjectValue(object)
	}

	def static dispatch appendPrettyValue(Description description, Resource resource) {
		description.appendResourceValue(resource)
	}

	def static dispatch appendPrettyValue(Description description, Object object) {
		description.appendValue(object)
	}

	def static dispatch appendPrettyValue(Description description, Void nul) {
		description.appendText('''<null>''')
	}

	def static appendPrettyValueList(Description description, List<?> objects) {
		new ModelPrinter(description).printList(objects)
	}

	def static appendPrettyValueSet(Description description, Set<?> objects) {
		new ModelPrinter(description).printSet(objects)
	}
}
