package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.SimpleParticipationImpl

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class SimpleParticipationI extends SimpleParticipationImpl {

	override getName() {
		alias ?: participationClass?.domain?.name
	}
	
	override toString() {
		'''«name»'''
	}
	
}
