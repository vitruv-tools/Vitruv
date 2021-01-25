package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.ParticipationAttributeImpl

package class ParticipationAttributeI extends ParticipationAttributeImpl {
	override getType() {
		getAttribute()?.type
	}

	override basicGetClassLikeContainer() {
		getParticipationClass()
	}

	override isMultiValued() {
		getAttribute() !== null && getAttribute().isMultiValued
	}

	override getName() {
		getAttribute()?.name
	}

	override toString() {
		'''«participationClass».«name»'''
	}
}
