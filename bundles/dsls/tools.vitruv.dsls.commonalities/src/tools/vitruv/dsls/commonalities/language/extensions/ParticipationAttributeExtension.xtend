package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ReferencedParticipationAttributeOperand

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*

@Utility
package class ParticipationAttributeExtension {

	static def getOptionalContainingParticipationAttributeOperand(ParticipationAttribute participationAttribute) {
		return participationAttribute.getOptionalContainer(ParticipationAttributeOperand)
	}

	static def getOptionalContainingReferencedParticipationAttributeOperand(
		ParticipationAttribute participationAttribute) {
		return participationAttribute.getOptionalContainer(ReferencedParticipationAttributeOperand)
	}

	static def OperatorReferenceMapping getOptionalContainingOperatorReferenceMapping(
		ParticipationAttribute participationAttribute) {
		return participationAttribute.getOptionalContainer(OperatorReferenceMapping)
	}

	static def ParticipationCondition getOptionalContainingParticipationCondition(
		ParticipationAttribute participationAttribute) {
		return participationAttribute.getOptionalContainer(ParticipationCondition)
	}
}
