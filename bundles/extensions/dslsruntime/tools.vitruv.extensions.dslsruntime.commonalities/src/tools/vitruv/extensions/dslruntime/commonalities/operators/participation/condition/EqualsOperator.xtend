package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import java.util.List
import java.util.Objects

@ParticipationConditionOperator(name = '=')
class EqualsOperator extends AbstractSingleArgumentOperator {

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
	}

	override check() {
		val value = leftOperandObject.eGet(leftOperandFeature)
		return Objects.equals(value, rightOperandValue)
	}

	override enforce() {
		leftOperandObject.eSet(leftOperandFeature, rightOperandValue)
	}

	private def Object getRightOperandValue() {
		if (rightOperand instanceof AttributeOperand) {
			val rightAttributeOperand = rightOperand as AttributeOperand
			return rightAttributeOperand.object.eGet(rightAttributeOperand.feature)
		} else {
			return rightOperand
		}
	}
}
