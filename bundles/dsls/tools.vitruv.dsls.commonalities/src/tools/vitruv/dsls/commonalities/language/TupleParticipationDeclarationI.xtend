package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.TupleParticipationDeclarationImpl

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class TupleParticipationDeclarationI extends TupleParticipationDeclarationImpl {
	
	override getName() {
		alias ?: classes.head?.domain?.name
	}
	
	override toString() {
		'''«name»'''
	}
	
}