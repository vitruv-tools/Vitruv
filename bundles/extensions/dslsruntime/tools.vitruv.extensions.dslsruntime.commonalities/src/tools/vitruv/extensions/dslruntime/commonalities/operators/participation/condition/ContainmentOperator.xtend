package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import com.google.common.base.Preconditions
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import tools.vitruv.extensions.dslruntime.commonalities.operators.AttributeOperand

import static extension tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentOperator.*

@ParticipationConditionOperator(name = 'in')
class ContainmentOperator extends AbstractSingleArgumentConditionOperator implements IParticipationClassConditionOperator {

	new(Object leftOperand, List<Object> rightOperands) {
		super(leftOperand, rightOperands)
		Preconditions.checkArgument(rightOperand instanceof EObject, "Right operand is not of type EObject")
	}

	override enforce() {
		val left = leftOperandObject
		val right = rightOperandObject
		val containmentReference = containmentReference
		if (containmentReference.upperBound != 1) {
			(right.eGet(containmentReference) as List<EObject>) += left
		} else {
			right.eSet(containmentReference, left)
		}
	}

	override check() {
		val left = leftOperandObject
		val right = rightOperandObject
		val containmentReference = containmentReference
		if (containmentReference.upperBound != 1) {
			return (right.eGet(containmentReference) as List<EObject>).contains(left)
		} else {
			return (right.eGet(containmentReference) == left)
		}
	}

	private def getRightOperandObject() {
		if (rightOperand instanceof AttributeOperand) {
			val rightAttributeOperand = rightOperand as AttributeOperand
			return rightAttributeOperand.object
		} else {
			return rightOperand as EObject
		}
	}

	private def EReference getContainmentReference() {
		val left = leftOperandObject
		if (rightOperand instanceof AttributeOperand) {
			val rightAttributeOperand = rightOperand as AttributeOperand
			return rightAttributeOperand.feature as EReference
		} else {
			// Guess the containment reference:
			val right = rightOperand as EObject
			return right.eClass.getContainmentReference(left.eClass)
		}
	}
}
