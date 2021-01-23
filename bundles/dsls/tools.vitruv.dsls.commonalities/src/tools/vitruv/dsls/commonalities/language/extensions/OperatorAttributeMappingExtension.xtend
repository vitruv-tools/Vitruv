package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.OperatorAttributeMapping
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClass

@Utility
package class OperatorAttributeMappingExtension {

	// Can return null
	static def getParticipationAttributeOperand(OperatorAttributeMapping mapping) {
		// Assert: There is at most one ParticipationAttributeOperand (ensured via validation)
		mapping.operands.filter(ParticipationAttributeOperand).head
	}

	static def getParticipationClassOperands(OperatorAttributeMapping mapping) {
		mapping.operands.filter(ParticipationClass)
	}

	/**
	 * Gets all operands that are common for both application directions (from participation to commonality and from
	 * commonality to participation).
	 * <p>
	 * I.e. this omits the participation attribute operand (if it is present).
	 */
	static def getCommonOperands(OperatorAttributeMapping mapping) {
		// Assert: Does not include any commonality attribute operand for the commonality attribute the mapping is
		// declared for. This is ensured via validation.
		mapping.operands.filter [ operand |
			!(operand instanceof ParticipationAttributeOperand)
		]
	}
}
