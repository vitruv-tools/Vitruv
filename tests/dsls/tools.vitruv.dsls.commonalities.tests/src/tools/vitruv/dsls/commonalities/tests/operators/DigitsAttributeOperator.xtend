package tools.vitruv.dsls.commonalities.tests.operators

import java.util.List
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Converts between an integer number on the participation side and a list of
 * its decimal digits on the commonality side.
 * <p>
 * <code>null</code> or an empty list on one side is converted to
 * <code>0</code> or an empty list on the respectively other side.
 */
@AttributeMappingOperator(
	name = 'digits',
	commonalityAttributeType = @AttributeType(multiValued = true, type = Integer),
	participationAttributeType = @AttributeType(multiValued = false, type = Integer)
)
class DigitsAttributeOperator extends AbstractAttributeMappingOperator<List<Integer>, Integer> {
	new(ReactionExecutionState executionState) {
		super(executionState)
	}

	override applyTowardsCommonality(Integer participationAttributeValue) {
		if (participationAttributeValue === null) return #[]
		val numberString = participationAttributeValue.toString
		return numberString.chars.map[Character.getNumericValue(it)].toArray
	}

	override applyTowardsParticipation(List<Integer> commonalityAttributeValue) {
		if (commonalityAttributeValue.nullOrEmpty) return 0
		return Integer.parseInt(commonalityAttributeValue.join)
	}
}
