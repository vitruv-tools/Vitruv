package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.dsls.commonalities.language.elements.impl.ParticipationClassImpl

package class ParticipationClassI extends ParticipationClassImpl {

	var attributesLoaded = false
	var referencesLoaded = false

	override getName() {
		alias ?: getSuperMetaclass()?.name
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
	
	override basicGetParticipation() {
		var EObject container = eContainer
		while (container !== null) {
			if (container instanceof Participation) {
				return container
			}
			container = container.eContainer
		}
		throw new IllegalStateException('''ParticipationCass is not contained in a Participation!''')
	}
	
	override eIsSet(EStructuralFeature eFeature) {
		super.eIsSet(eFeature)
	}
	
}
