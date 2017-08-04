package tools.vitruv.extensions.dslsruntime.mappings

import org.eclipse.xtend.lib.annotations.Accessors

abstract class MappingInstance<L extends MappingInstanceHalf,R extends MappingInstanceHalf> {
	@Accessors(PROTECTED_GETTER) L leftHalf
	@Accessors(PROTECTED_GETTER) R rightHalf
	
	new(L leftHalf, R rightHalf) {
		this.leftHalf = leftHalf
		this.rightHalf = rightHalf
	}

	def boolean checkLeftConditions() {
		return leftHalf.checkConditions()
	}
	
	def void enforceLeftConditions() {
		leftHalf.enforceConditions()
	}
	
	def boolean checkRightConditions() {
		return rightHalf.checkConditions()
	}
	
	def void enforceRigthConditions() {
		rightHalf.enforceConditions
	}
	
	def void enforceFromLeft2Right() {
		// empty
	}
	
	def void enforceFromRight2Left() {
		// empty
	}
}