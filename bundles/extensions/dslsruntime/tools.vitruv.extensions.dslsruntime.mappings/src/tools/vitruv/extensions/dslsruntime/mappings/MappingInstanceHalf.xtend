package tools.vitruv.extensions.dslsruntime.mappings

/**
 * Marker interface for data structures that group elements of a metamodel for an instantiation of a mapping, which relates instances of two metamodels.
 */
interface MappingInstanceHalf {
	
	def boolean checkConditions() {
		return true
	}
	
	def void enforceConditions() {
		// empty
	}
	
	def boolean contains(Object element)
}