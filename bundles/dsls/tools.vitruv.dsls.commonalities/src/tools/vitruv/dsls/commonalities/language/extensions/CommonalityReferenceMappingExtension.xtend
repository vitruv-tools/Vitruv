package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ReferenceEqualitySpecification
import tools.vitruv.dsls.commonalities.language.ReferenceReadSpecification
import tools.vitruv.dsls.commonalities.language.ReferenceSetSpecification

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationClassExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationExtension.*

@Utility
package class CommonalityReferenceMappingExtension {

	def static dispatch isRead(ReferenceReadSpecification mapping) {
		true
	}

	def static dispatch isRead(ReferenceEqualitySpecification mapping) {
		true
	}

	def static dispatch isRead(ReferenceSetSpecification mapping) {
		false
	}

	def static dispatch isWrite(ReferenceReadSpecification mapping) {
		false
	}

	def static dispatch isWrite(ReferenceEqualitySpecification mapping) {
		true
	}

	def static dispatch isWrite(ReferenceSetSpecification mapping) {
		true
	}

	def static getParticipation(CommonalityReferenceMapping mapping) {
		mapping.reference.participationClass.participation
	}

	def static getDeclaringReference(CommonalityReferenceMapping mapping) {
		return mapping.getDirectContainer(CommonalityReference)
	}

	def static getReferencedCommonality(CommonalityReferenceMapping mapping) {
		return mapping.declaringReference.referenceType
	}

	def static getReferencedParticipation(CommonalityReferenceMapping mapping) {
		val participationDomainName = mapping.participation.domainName
		val referencedCommonality = mapping.referencedCommonality
		// Note: We verify via validation that there is exactly one matching participation.
		return referencedCommonality.participations.findFirst [
			it.domainName == participationDomainName
			// TODO Support for multiple participations with the same domain inside the referenced commonality.
		]
	}

	def static isAssignmentCompatible(CommonalityReferenceMapping mapping, ParticipationClass referencedClass) {
		val referenceType = mapping.reference.type
		return referenceType.isSuperTypeOf(referencedClass.superMetaclass)
	}
}
