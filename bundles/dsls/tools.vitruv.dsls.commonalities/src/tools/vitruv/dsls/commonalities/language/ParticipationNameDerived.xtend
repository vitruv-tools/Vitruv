package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.elements.Participation

package interface ParticipationNameDerived extends Participation {
	
	override getName() {
		alias ?: getDomain()?.name
	}
}