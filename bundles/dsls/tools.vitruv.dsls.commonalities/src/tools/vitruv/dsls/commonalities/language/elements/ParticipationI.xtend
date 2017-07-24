package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.ParticipationImpl

package class ParticipationI extends ParticipationImpl {
	
	override getName() {
		alias ?: domain?.name
	}
	
	override basicGetDomain() {
		classes.findFirst [true]?.superMetaclass?.domain
	}
	
}