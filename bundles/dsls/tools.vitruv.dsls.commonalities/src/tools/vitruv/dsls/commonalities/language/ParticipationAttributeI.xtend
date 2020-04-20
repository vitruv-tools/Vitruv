package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.ParticipationAttributeImpl

package class ParticipationAttributeI extends ParticipationAttributeImpl {

	override getType() {
		attribute?.type
	}

	override basicGetClassLikeContainer() {
		participationClass
	}

	override isMultiValued() {
		attribute?.isMultiValued
	}

	override getName() {
		attribute?.name
	}

	override toString() {
		'''«participationClass».«name»'''
	}
}
