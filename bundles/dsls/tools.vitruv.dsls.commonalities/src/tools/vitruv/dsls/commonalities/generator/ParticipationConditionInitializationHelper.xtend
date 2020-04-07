package tools.vitruv.dsls.commonalities.generator

import java.util.List
import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.LiteralOperand
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ParticipationConditionOperand
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AttributeOperand

import static tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationConditionInitializationHelper extends ReactionsGenerationHelper {

	static val CONDITION_ENFORCE_METHOD = 'enforce'

	package new() {
	}

	def getParticipationConditionsInitializers(ParticipationClass participationClass) {
		return participationClass.participation.conditions
			.filter[enforced]
			.filter[leftOperand.participationClass == participationClass]
			.map[participationConditionInitializer]
	}

	def private Function<TypeProvider, XExpression> getParticipationConditionInitializer(
		ParticipationCondition participationCondition) {
		return [ extension TypeProvider typeProvider |
			val operator = participationCondition.operator.imported
			val enforceMethod = operator.findMethod(CONDITION_ENFORCE_METHOD)
			return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = participationCondition.newParticipationConditionOperator(typeProvider)
				feature = enforceMethod
				explicitOperationCall = true
			]
		]
	}

	def private newParticipationConditionOperator(ParticipationCondition participationCondition,
		extension TypeProvider typeProvider) {
		val leftOperand = participationCondition.leftOperand
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			constructor = participationCondition.operator.findConstructor(Object, List)
			explicitConstructorCall = true
			arguments += expressions(
				leftOperand.getOperandExpression(typeProvider),
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += participationCondition.rightOperands.map [
						getOperandExpression(typeProvider)
					]
				]
			)
		]
	}

	def private dispatch getOperandExpression(LiteralOperand operand,
		extension TypeProvider typeProvider) {
		return operand.expression
	}

	def private dispatch getOperandExpression(ParticipationClassOperand operand,
		extension TypeProvider typeProvider) {
		return variable(operand.participationClass.correspondingVariableName)
	}

	def private dispatch getOperandExpression(ParticipationAttributeOperand operand,
		extension TypeProvider typeProvider) {
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

	def private dispatch getOperandExpression(ParticipationConditionOperand operand,
		extension TypeProvider typeProvider) {
		throw new IllegalStateException("Unhandled ParticipationConditionOperand type: " + operand.class.name)
	}
}