package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping
import tools.vitruv.dsls.commonalities.language.ReferencedParticipationAttributeOperand

@Utility
package class OperatorReferenceMappingExtension {

	def static getReferencedParticipationAttributes(OperatorReferenceMapping mapping) {
		return mapping.operands
			.filter(ReferencedParticipationAttributeOperand)
			.map[participationAttribute]
	}

	def static getReferencedParticipationClasses(OperatorReferenceMapping mapping) {
		return mapping.referencedParticipationAttributes.map[participationClass]
	}
}
