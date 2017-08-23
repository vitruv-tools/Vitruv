package tools.vitruv.extensions.dslsruntime.mappings

import java.util.List
import org.eclipse.emf.ecore.EObject

/**
 * Base class for data structures that group elements of a metamodel for an instantiation of a mapping, which relates instances of two metamodels.
 */
abstract class MappingInstanceHalf {
	def boolean checkConditions() {
		var allElementsStillExisting = true
		for (element : getElements()) {
			if (element.eResource === null) {
				allElementsStillExisting = false
			}
		}
		return allElementsStillExisting
	}
	
	def void enforceConditions() {
		// empty
	}
	
	def List<EObject> getElements()
	
	def boolean contains(EObject element) {
		return getElements().contains(element)
	}
}