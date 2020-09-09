package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import com.google.common.base.Preconditions
import java.util.List

abstract class AbstractNoArgumentConditionOperator extends AbstractParticipationConditionOperator {

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		Preconditions.checkArgument(rightOperands.empty, "This operator does not expect any right operand(s)!")
	}
}
