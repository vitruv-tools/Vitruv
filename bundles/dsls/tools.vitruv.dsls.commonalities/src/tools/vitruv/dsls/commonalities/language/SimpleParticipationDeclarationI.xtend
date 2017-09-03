package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.SimpleParticipationDeclarationImpl

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class SimpleParticipationDeclarationI extends SimpleParticipationDeclarationImpl {

	override getName() {
		alias ?: participationClass?.domain?.name
	}
	
	override toString() {
		'''«name»'''
	}
	
}
