package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
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
import tools.vitruv.dsls.commonalities.language.ParticipationConditionLeftOperand
import tools.vitruv.dsls.commonalities.language.ParticipationConditionRightOperand
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AttributeOperand

import static tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationObjectInitializationHelper extends ReactionsGenerationHelper {

	@Inject extension ResourceBridgeHelper resourceBridgeHelper
	@Inject ParticipationRelationInitializationBuilder.Factory participationRelationInitializationBuilder

	package new() {
	}

	def toBlockExpression(Iterable<Function<TypeProvider, XExpression>> expressionBuilders,
		TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressionBuilders.map[apply(typeProvider)]
		]
	}

	/**
	 * Early initializations that only affect the specific participation object
	 * itself.
	 */
	def getInitializers(ParticipationClass participationClass) {
		return #[
			participationClass.resourceInitializer,
			participationClass.commonalityParticipationClassInitializer
		].filterNull
	}

	def private Function<TypeProvider, XExpression> getResourceInitializer(ParticipationClass participationClass) {
		if (!participationClass.isForResource) return null
		return [ extension TypeProvider typeProvider |
			val resourceBridge = variable(participationClass.correspondingVariableName)
			participationClass.initNewResourceBridge(resourceBridge, typeProvider)
		]
	}

	def private Function<TypeProvider, XExpression> getCommonalityParticipationClassInitializer(
		ParticipationClass participationClass) {
		if (!participationClass.participation.isCommonalityParticipation) return null
		return [ extension TypeProvider typeProvider |
			assignStagingId(typeProvider, variable(participationClass.correspondingVariableName))
		]
	}

	/**
	 * Initializations that need to happen after all participation objects have
	 * been created.
	 * <p>
	 * For example, this includes initializations done by operators since they
	 * may want to reference other participation objects.
	 */
	def getParticipationClassPostInitializers(ParticipationClass participationClass) {
		return (#[
			participationClass.participationRelationInitializer
		] + participationClass.participationClassConditionInitializers).filterNull.toList
	}

	def private Function<TypeProvider, XExpression> getParticipationRelationInitializer(
		ParticipationClass participationClass) {
		val relationInitializationBuilder = participationRelationInitializationBuilder.createFor(participationClass)
		if (!relationInitializationBuilder.hasInitializer) return null
		return [ TypeProvider typeProvider |
			relationInitializationBuilder.getInitializer(typeProvider, typeProvider.participationClassToObject)
		]
	}

	def Function<ParticipationClass, XExpression> participationClassToObject(
		extension TypeProvider typeProvider) {
		return [variable(correspondingVariableName)]
	}

	def private getParticipationClassConditionInitializers(ParticipationClass participationClass) {
		return participationClass.participation.conditions
			.filter[enforced]
			.filter[leftOperand.participationClass == participationClass]
			.map[participationConditionInitializer]
	}

	def private Function<TypeProvider, XExpression> getParticipationConditionInitializer(
		ParticipationCondition participationCondition) {
		return [ extension TypeProvider typeProvider |
			val operator = participationCondition.operator.imported
			val enforceMethod = operator.findOptionalImplementedMethod("enforce")
			return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = participationCondition.newParticipationConditionOperator(typeProvider)
				feature = enforceMethod
				explicitOperationCall = true
			]
		]
	}

	def package newParticipationConditionOperator(ParticipationCondition participationCondition,
		extension TypeProvider typeProvider) {
		val leftOperand = participationCondition.leftOperand
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			constructor = participationCondition.operator.findConstructor(Object, List)
			explicitConstructorCall = true
			arguments += expressions(
				leftOperand.getParticipationConditionLeftOperandExpression(typeProvider),
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += participationCondition.rightOperands.map [
						getParticipationConditionOperandExpression(typeProvider)
					]
				]
			)
		]
	}

	def private getParticipationConditionLeftOperandExpression(ParticipationConditionLeftOperand leftOperand,
		extension TypeProvider typeProvider) {
		val participationClass = leftOperand.participationClass
		val participationClassInstance = typeProvider.variable(participationClass.correspondingVariableName)
		val attribute = leftOperand.attribute // can be null
		if (attribute === null) {
			return participationClassInstance
		} else {
			return XbaseFactory.eINSTANCE.createXConstructorCall => [
				val operandType = typeProvider.findDeclaredType(AttributeOperand)
				constructor = operandType.findConstructor(EObject, EStructuralFeature)
				explicitConstructorCall = true
				arguments += expressions(
					participationClassInstance,
					getEFeature(typeProvider, participationClassInstance, attribute.name)
				)
			]
		}
	}

	def private dispatch getParticipationConditionOperandExpression(LiteralOperand operand,
		extension TypeProvider typeProvider) {
		return operand.expression
	}

	def private dispatch getParticipationConditionOperandExpression(ParticipationClassOperand operand,
		extension TypeProvider typeProvider) {
		return variable(operand.participationClass.correspondingVariableName)
	}

	def private dispatch getParticipationConditionOperandExpression(ParticipationAttributeOperand operand,
		extension TypeProvider typeProvider) {
		val attribute = operand.participationAttribute
		val participationClass = attribute.participationClass
		return getEFeature(typeProvider, variable(participationClass.correspondingVariableName), attribute.name)
	}

	def private dispatch getParticipationConditionOperandExpression(ParticipationConditionRightOperand operand,
		extension TypeProvider typeProvider) {
		throw new IllegalStateException("Unhandled ParticipationConditionRightOperand type: " + operand.class.name)
	}
}
