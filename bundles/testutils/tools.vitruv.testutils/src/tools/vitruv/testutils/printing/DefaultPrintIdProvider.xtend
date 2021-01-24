package tools.vitruv.testutils.printing

import java.util.HashMap
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject

class DefaultPrintIdProvider implements PrintIdProvider {
	val printed = new HashMap<EObject, String>()
	val classCount = new HashMap<EClass, Integer>()

	override <T extends EObject> ifAlreadyPrintedElse(T object, (T, String)=>PrintResult existingPrinter,
		(T, String)=>PrintResult newPrinter) {
		var objectId = printed.get(object)
		if (objectId !== null) {
			existingPrinter.apply(object, objectId)
		} else {
			objectId = assignId(object)
			printed.put(object, objectId)
			newPrinter.apply(object, objectId)
		}
	}

	def private assignId(EObject object) {
		val index = classCount.compute(object.eClass) [ key, oldValue |
			if (oldValue === null) 1 else oldValue + 1
		]
		object.eClass.name + "#" + index
	}
}
