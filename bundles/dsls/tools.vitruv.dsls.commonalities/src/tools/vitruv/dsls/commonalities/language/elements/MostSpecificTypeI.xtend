package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.MostSpecificTypeImpl

class MostSpecificTypeI extends MostSpecificTypeImpl {

	override isSuperTypeOf(Classifier subType) {
		return (this === subType)
	}

	override getName() {
		return 'MostSpecificType'
	}

	override toString() {
		return 'MostSpecificType'
	}
}
