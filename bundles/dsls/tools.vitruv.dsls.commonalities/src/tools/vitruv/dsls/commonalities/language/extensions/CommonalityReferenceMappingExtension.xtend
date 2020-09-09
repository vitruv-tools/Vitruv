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

	static def isSimpleMapping(CommonalityReferenceMapping mapping) {
		return (mapping instanceof SimpleReferenceMapping)
	}

	static def isOperatorMapping(CommonalityReferenceMapping mapping) {
		return (mapping instanceof OperatorReferenceMapping)
	}

	static def dispatch boolean isMultiValued(SimpleReferenceMapping mapping) {
		return mapping.reference.isMultiValued
	}

	static def dispatch boolean isMultiValued(OperatorReferenceMapping mapping) {
		return mapping.operator.isMultiValued
	}

	static def dispatch ParticipationClass getParticipationClass(SimpleReferenceMapping mapping) {
		return mapping.reference.participationClass
	}

	static def dispatch ParticipationClass getParticipationClass(OperatorReferenceMapping mapping) {
		return mapping.participationClass
	}

	static def Participation getParticipation(CommonalityReferenceMapping mapping) {
		return mapping.participationClass.participation
	}

	static def getDeclaringReference(CommonalityReferenceMapping mapping) {
		return mapping.getDirectContainer(CommonalityReference)
	}

	static def getReferencedCommonality(CommonalityReferenceMapping mapping) {
		return mapping.declaringReference.referenceType
	}

	static def getReferencedParticipation(CommonalityReferenceMapping mapping) {
		val participationDomainName = mapping.participation.domainName
		val referencedCommonality = mapping.referencedCommonality
		// Note: We verify via validation that there is exactly one matching participation.
		val participation = referencedCommonality.participations.findFirst [
			it.domainName == participationDomainName
			// TODO Support for multiple participations with the same domain inside the referenced commonality.
		]
		if (participation === null) {
			// This is usually a hint for a scoping related issue:
			throw new RuntimeException('''Could not find referenced participation '«participationDomainName
				»' in commonality '«referencedCommonality»' for mapping of reference '«
				mapping.declaringReference»'. ''')
		}
		return participation
	}

	static def dispatch boolean isAssignmentCompatible(SimpleReferenceMapping mapping,
		ParticipationClass referencedClass) {
		val referenceType = mapping.reference.type
		return referenceType.isSuperTypeOf(referencedClass.superMetaclass)
	}

	static def dispatch boolean isAssignmentCompatible(OperatorReferenceMapping mapping,
		ParticipationClass referencedClass) {
		// depends on the operator, for which we have no compile-time checking currently
		return true
	}
}
