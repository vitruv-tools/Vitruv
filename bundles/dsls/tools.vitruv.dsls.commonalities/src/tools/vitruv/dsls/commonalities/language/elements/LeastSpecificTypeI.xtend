package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.LeastSpecificTypeImpl

class LeastSpecificTypeI extends LeastSpecificTypeImpl {

	override isSuperTypeOf(Classifier subType) {
		return true
	}

	override getName() {
		return 'LeastSpecificType'
	}

	override toString() {
		return 'LeastSpecificType'
	}
}
