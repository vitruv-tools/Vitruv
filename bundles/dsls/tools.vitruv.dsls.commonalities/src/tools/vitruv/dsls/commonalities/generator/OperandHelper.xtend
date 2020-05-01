package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.LiteralOperand
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
import tools.vitruv.dsls.commonalities.language.ParticipationConditionOperand
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperand
import tools.vitruv.dsls.commonalities.language.ReferencedParticipationAttributeOperand
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AttributeOperand

import static tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

@GenerationScoped
package class OperandHelper extends ReactionsGenerationHelper {

	package new() {
	}

	def dispatch getOperandExpression(LiteralOperand operand, extension TypeProvider typeProvider) {
		return operand.expression.copy
	}

	def dispatch getOperandExpression(ParticipationClassOperand operand, extension TypeProvider typeProvider) {
		return variable(operand.participationClass.correspondingVariableName)
	}

	def dispatch getOperandExpression(ParticipationAttributeOperand operand, extension TypeProvider typeProvider) {
		val attribute = operand.participationAttribute
		val participationClass = attribute.participationClass
		val participationClassInstance = variable(participationClass.correspondingVariableName)
		return XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operandType = typeProvider.findDeclaredType(AttributeOperand)
			constructor = operandType.findConstructor(EObject, EStructuralFeature)
			explicitConstructorCall = true
			arguments += expressions(
				participationClassInstance,
				getEFeature(typeProvider, participationClassInstance.copy, attribute.name)
			)
		]
	}

	def dispatch getOperandExpression(ReferencedParticipationAttributeOperand operand, extension TypeProvider typeProvider) {
		return null
	}

	def dispatch getOperandExpression(ParticipationConditionOperand operand, extension TypeProvider typeProvider) {
		throw new IllegalStateException("Unhandled ParticipationConditionOperand type: " + operand.class.name)
	}

	def dispatch getOperandExpression(ReferenceMappingOperand operand, extension TypeProvider typeProvider) {
		throw new IllegalStateException("Unhandled ReferenceMappingOperand type: " + operand.class.name)
	}
}
