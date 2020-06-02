package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.LiteralOperand
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
		return mapping.referencedParticipationAttributes.map[participationClass].toSet
	}

	// Gets the operands that are passed to the operator via its constructor:
	def static getPassedOperands(OperatorReferenceMapping mapping) {
		// Does not include any attribute operands:
		return mapping.operands.filter(LiteralOperand)
	}
}
