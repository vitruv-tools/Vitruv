package tools.vitruv.change.testutils.printing

import java.util.HashMap
import org.eclipse.emf.ecore.EObject

class DefaultPrintIdProvider implements PrintIdProvider {
	val printed = new HashMap<Object, String>()
	val classCount = new HashMap<Object, Integer>()

	override getFallbackId(Object object) {
		printed.computeIfAbsent(object) [ theObject |
			val classKey = switch (theObject) {
				EObject: theObject.eClass
				default: theObject.class
			}
			classCount.compute(classKey) [ key, value |
				if (value === null) 1 else value + 1
			].toString()
		]
	}
}
