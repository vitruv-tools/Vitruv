package tools.vitruv.dsls.commonalities.generator.reactions.operator

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeOperand
import tools.vitruv.dsls.commonalities.language.LiteralOperand
import tools.vitruv.dsls.commonalities.language.Operand
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
import tools.vitruv.dsls.commonalities.language.ReferencedParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.elements.Attribute
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.AttributeOperand

import static tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*

class OperandHelper extends ReactionsGenerationHelper {

	package new() {
	}

	def getOperandExpressions(Iterable<? extends Operand> operands, OperatorContext context) {
		return operands.map[getOperandExpression(context)].filterNull
	}

	def dispatch getOperandExpression(LiteralOperand operand, OperatorContext context) {
		return operand.expression.copy
	}

	def dispatch getOperandExpression(ParticipationClassOperand operand, OperatorContext context) {
		return context.getParticipationObject(operand.participationClass)
	}

	def dispatch getOperandExpression(ParticipationAttributeOperand operand, OperatorContext context) {
		val attribute = operand.participationAttribute
		val participationClass = attribute.participationClass
		val participationObject = context.getParticipationObject(participationClass)
		val passValue = context.passParticipationAttributeValues
		return context.getAttributeOperandOrValue(participationObject, attribute, passValue)
	}

	def dispatch getOperandExpression(CommonalityAttributeOperand operand, OperatorContext context) {
		val attribute = operand.attributeReference.attribute
		val intermediate = context.intermediate
		val passValue = context.passCommonalityAttributeValues
		return context.getAttributeOperandOrValue(intermediate, attribute, passValue)
	}


	def dispatch getOperandExpression(ReferencedParticipationAttributeOperand operand, OperatorContext context) {
		// This is used in reference mappings and not actually passed to operators.
		return null
	}

	def dispatch getOperandExpression(Operand operand, OperatorContext context) {
		throw new IllegalStateException("Unhandled operand type: " + operand.class.name)
	}

	private def getAttributeOperandOrValue(OperatorContext context, XExpression object, Attribute attribute,
		boolean passValue) {
		if (passValue) {
			return getAttributeValue(context.typeProvider, object, attribute)
		} else {
			return getAttributeOperand(context.typeProvider, object, attribute)
		}
	}

	private def getAttributeOperand(extension TypeProvider typeProvider, XExpression object, Attribute attribute) {
		return XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operandType = typeProvider.findDeclaredType(AttributeOperand)
			constructor = operandType.findConstructor(EObject, EStructuralFeature)
			explicitConstructorCall = true
			arguments += expressions(
				object,
				getEFeature(typeProvider, object.copy, attribute.correspondingEFeature)
			)
		]
	}

	private def getAttributeValue(extension TypeProvider typeProvider, XExpression object, Attribute attribute) {
		return getFeatureValue(typeProvider, object, attribute.correspondingEFeature)
	}
}
