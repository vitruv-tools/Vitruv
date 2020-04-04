package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import com.google.common.base.Preconditions
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslruntime.commonalities.operators.OperatorName

import static extension tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentRelation.*

@OperatorName('in')
class ContainmentOperator extends AbstractSingleArgumentOperator implements ParticipationClassConditionOperator {

	new(Object leftOperand, List<Object> rightOperands) {
		super(leftOperand, rightOperands)
		Preconditions.checkArgument(rightOperand instanceof EObject, "Right operand is not of type EObject")
	}

	private def getRightOperandObject() {
		return rightOperand as EObject
	}

	override enforce() {
		val left = leftOperandObject
		val right = rightOperandObject
		val containmentFeature = right.eClass.getContainmentFeature(left.eClass)
		if (containmentFeature.upperBound != 1) {
			(right.eGet(containmentFeature) as List<EObject>) += left
		} else {
			right.eSet(containmentFeature, left)
		}
	}

	override check() {
		val left = leftOperandObject
		val right = rightOperandObject
		val containmentFeature = right.eClass.getContainmentFeature(left.eClass)
		if (containmentFeature.upperBound != 1) {
			return (right.eGet(containmentFeature) as List<EObject>).contains(left)
		} else {
			return (right.eGet(containmentFeature) == left)
		}
	}
}
