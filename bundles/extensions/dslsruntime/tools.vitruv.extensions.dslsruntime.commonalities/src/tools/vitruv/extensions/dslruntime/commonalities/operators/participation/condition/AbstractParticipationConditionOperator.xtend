package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import com.google.common.base.Preconditions
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.extensions.dslruntime.commonalities.operators.AttributeOperand

abstract class AbstractParticipationConditionOperator implements IParticipationConditionOperator {

	val protected EObject leftOperandObject
	val protected EStructuralFeature leftOperandFeature // null for ParticipationClassConditionOperators
	val protected List<?> rightOperands // can be empty, may contain AttributeOperands

	// leftOperand: either a participation class instance or an AttributeOperand
	new(Object leftOperand, List<?> rightOperands) {
		Preconditions.checkNotNull(leftOperand, "Left operand is null")
		Preconditions.checkNotNull(rightOperands, "Right operands is null")
		if (this instanceof IParticipationClassConditionOperator) {
			Preconditions.checkArgument(leftOperand instanceof EObject, "This operator expects a model object as left operand")
			this.leftOperandObject = leftOperand as EObject
			this.leftOperandFeature = null
		} else {
			Preconditions.checkArgument(leftOperand instanceof AttributeOperand, "This operator expects an attribute as left operand")
			val leftAttributeOperand = leftOperand as AttributeOperand
			this.leftOperandObject = leftAttributeOperand.object
			this.leftOperandFeature = leftAttributeOperand.feature
		}
		this.rightOperands = rightOperands
	}
}
