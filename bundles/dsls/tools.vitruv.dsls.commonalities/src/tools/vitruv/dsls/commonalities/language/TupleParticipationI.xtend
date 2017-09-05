package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.TupleParticipationImpl

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class TupleParticipationI extends TupleParticipationImpl {
	
	override getName() {
		alias ?: classes.head?.domain?.name
	}
	
	override toString() {
		'''«name»'''
	}
	
}