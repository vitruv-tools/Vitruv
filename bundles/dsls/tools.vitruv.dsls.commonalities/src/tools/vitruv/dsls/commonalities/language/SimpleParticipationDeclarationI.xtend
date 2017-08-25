package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.elements.Domain
import tools.vitruv.dsls.commonalities.language.impl.SimpleParticipationDeclarationImpl

package class SimpleParticipationDeclarationI extends SimpleParticipationDeclarationImpl implements ParticipationNameDerived {

	override getClasses() {
		if (classes === null) {
			super.getClasses().add(participationClass)
		}
		classes
	}

	override setDomain(Domain newDomain) {
		if (newDomain !== null) {
			throw new UnsupportedOperationException('''Cannot set the domain on a «class.simpleName»''')
		}
	}

	def private deriveDomain() {
		domain = getClasses().head?.superMetaclass.domain
	}

	override getDomain() {
		basicGetDomain() ?: deriveDomain()
	}

	override getName() {
		ParticipationNameDerived.super.getName()
	}
	
	override eIsSet(int featureID) {
		switch featureID {
			case LanguagePackage.PARTICIPATION_DECLARATION__DOMAIN:
				super.eIsSet(LanguagePackage.PARTICIPATION_DECLARATION__CLASSES)
				
			default:
				super.eIsSet(featureID)
		}
	}
	
}
