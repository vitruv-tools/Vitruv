package tools.vitruv.extensions.dslsruntime.mappings

import java.util.List

/**
 * Base class for data structures that group elements of a metamodel for an instantiation of a mapping, which relates instances of two metamodels.
 */
abstract class MappingInstanceHalf {
	def boolean checkConditions() {
		return true
	}
	
	def void enforceConditions() {
		// empty
	}
	
	def List<Object> getElements()
	
	def boolean contains(Object element) {
		return getElements().contains(element)
	}
}