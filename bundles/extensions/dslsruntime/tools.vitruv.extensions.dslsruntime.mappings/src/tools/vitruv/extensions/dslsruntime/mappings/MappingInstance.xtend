package tools.vitruv.extensions.dslsruntime.mappings

interface MappingInstance {
	// Rationale MK: This is not an abstract class with type parameters for two extension fields for left and right halves,
	// because Xtend does not make extension fields of superclasses available in subclasses. 

	def boolean checkLeftConditions() {
		return true
	}
	
	def void enforceLeftConditions() {
		// empty
	}
	
	def boolean checkRightConditions() {
		return true
	}
	
	def void enforceRigthConditions() {
		// empty
	}
	
	def void enforceFromLeft2Right() {
		// empty
	}
	
	def void enforceFromRight2Left() {
		// empty
	}
}