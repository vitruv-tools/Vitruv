package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.ParticipationClassImpl

package class ParticipationClassI extends ParticipationClassImpl {

	var attributesLoaded = false
	var referencesLoaded = false

	override getName() {
		alias ?: superMetaclass.name
	}
	
	def private isSuperMetaclassSet() {
		return superMetaclass !== null
	}

	override getAttributes() {
		super.getAttributes() => [
			if (superMetaclassSet && !attributesLoaded) {
				addAll(loadAttributes())
				attributesLoaded = true	
			}
		]
	}

	override getReferences() {
		super.getReferences() => [
			if (superMetaclassSet && !referencesLoaded) {
				addAll(loadReferences())
				referencesLoaded = true
			}
		]
	}

	def private loadAttributes() {
		superMetaclass.attributes.map [ attribute |
			LanguageElementsFactory.eINSTANCE.createParticipationAttribute() => [
				wrapAttribute(attribute)
			]
		]
	}

	def private loadReferences() {
		superMetaclass.references.map [ reference |
			LanguageElementsFactory.eINSTANCE.createParticipationReference() => [
				wrapReference(reference)
			]
		]
	}
	
	override basicGetPackageLikeContainer() {
		participation
	}
	
}
