package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.SimpleReferenceMapping

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationClassExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ReferenceMappingOperatorExtension.*

@Utility
package class CommonalityReferenceMappingExtension {

	def static isSimpleMapping(CommonalityReferenceMapping mapping) {
		return (mapping instanceof SimpleReferenceMapping)
	}

	def static isOperatorMapping(CommonalityReferenceMapping mapping) {
		return (mapping instanceof OperatorReferenceMapping)
	}

	def static dispatch boolean isMultiValued(SimpleReferenceMapping mapping) {
		return mapping.reference.isMultiValued
	}

	def static dispatch boolean isMultiValued(OperatorReferenceMapping mapping) {
		return mapping.operator.isMultiValued
	}

	def static dispatch ParticipationClass getParticipationClass(SimpleReferenceMapping mapping) {
		return mapping.reference.participationClass
	}

	def static dispatch ParticipationClass getParticipationClass(OperatorReferenceMapping mapping) {
		return mapping.participationClass
	}

	def static Participation getParticipation(CommonalityReferenceMapping mapping) {
		return mapping.participationClass.participation
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

	def static dispatch boolean isAssignmentCompatible(SimpleReferenceMapping mapping,
		ParticipationClass referencedClass) {
		val referenceType = mapping.reference.type
		return referenceType.isSuperTypeOf(referencedClass.superMetaclass)
	}

	def static dispatch boolean isAssignmentCompatible(OperatorReferenceMapping mapping,
		ParticipationClass referencedClass) {
		// depends on the operator, for which we have no compile-time checking currently
		return true
	}
}
