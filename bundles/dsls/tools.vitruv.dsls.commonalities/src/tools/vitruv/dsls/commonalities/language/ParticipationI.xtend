package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.ParticipationImpl

package class ParticipationI extends ParticipationImpl {

	override getName() {
		domainAlias ?: domainName
	}

	override toString() {
		'''«name»'''
	}
}
