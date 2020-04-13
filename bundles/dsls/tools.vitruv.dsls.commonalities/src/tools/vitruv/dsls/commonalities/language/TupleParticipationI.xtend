package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.TupleParticipationImpl

package class TupleParticipationI extends TupleParticipationImpl {

	override getName() {
		alias ?: domainName
	}

	override toString() {
		'''«name»'''
	}
}
