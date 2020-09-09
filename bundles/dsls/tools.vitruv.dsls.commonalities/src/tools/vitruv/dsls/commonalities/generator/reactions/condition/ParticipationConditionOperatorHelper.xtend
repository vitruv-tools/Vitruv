package tools.vitruv.dsls.commonalities.generator.reactions.condition

import com.google.inject.Inject
import java.util.List
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.operator.OperandHelper
import tools.vitruv.dsls.commonalities.generator.reactions.operator.OperatorContext
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.IParticipationConditionOperator

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*

class ParticipationConditionOperatorHelper extends ReactionsGenerationHelper {

	static class ParticipationConditionOperatorContext implements OperatorContext {

		val extension TypeProvider typeProvider

		new(TypeProvider typeProvider) {
			checkNotNull(typeProvider, "typeProvider is null")
			this.typeProvider = typeProvider
		}

		override getTypeProvider() {
			return typeProvider
		}

		private def unsupportedOperationException() {
			return new UnsupportedOperationException("Unsupported in participation condition context!")
		}

		override passParticipationAttributeValues() {
			// We pass AttributeOperands because participation conditions have write access:
			return false
		}

		override passCommonalityAttributeValues() {
			throw unsupportedOperationException()
		}

		override getIntermediate() {
			throw unsupportedOperationException()
		}

		override getParticipationObject(ParticipationClass participationClass) {
			return variable(participationClass.correspondingVariableName)
		}
	}

	static val ENFORCE_METHOD = 'enforce'
	static val CHECK_METHOD = 'check'

	@Inject extension OperandHelper operandHelper

	package new() {
	}

	def constructOperator(ParticipationCondition participationCondition,
		ParticipationConditionOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val leftOperand = participationCondition.leftOperand
		return XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operatorType = participationCondition.operator.jvmType.imported
			constructor = operatorType.findConstructor(Object, List)
			explicitConstructorCall = true
			arguments += leftOperand.getOperandExpression(operatorContext)
			arguments += XbaseFactory.eINSTANCE.createXListLiteral => [
				elements += participationCondition.rightOperands.map [
					getOperandExpression(operatorContext)
				]
			]
		]
	}

	private def callOperatorMethod(ParticipationCondition participationCondition, JvmOperation method,
		ParticipationConditionOperatorContext operatorContext) {
		return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = participationCondition.constructOperator(operatorContext)
			feature = method
			explicitOperationCall = true
		]
	}

	def XExpression enforce(ParticipationCondition participationCondition,
		ParticipationConditionOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val method = typeProvider.findMethod(IParticipationConditionOperator, ENFORCE_METHOD)
		return participationCondition.callOperatorMethod(method, operatorContext)
	}

	def XExpression check(ParticipationCondition participationCondition,
		ParticipationConditionOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val method = typeProvider.findMethod(IParticipationConditionOperator, CHECK_METHOD)
		return participationCondition.callOperatorMethod(method, operatorContext)
	}
}
