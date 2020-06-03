package tools.vitruv.dsls.commonalities.generator.reactions.condition

import com.google.inject.Inject
import java.util.List
import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.operator.OperandHelper
import tools.vitruv.dsls.commonalities.generator.reactions.operator.OperatorContext
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@GenerationScoped
class ParticipationConditionInitializationHelper extends ReactionsGenerationHelper {

	private static class ParticipationConditionOperatorContext implements OperatorContext {

		val extension TypeProvider typeProvider

		new(TypeProvider typeProvider) {
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

	static val CONDITION_ENFORCE_METHOD = 'enforce'

	@Inject extension OperandHelper operandHelper

	package new() {
	}

	def getParticipationConditionsInitializers(ParticipationContext participationContext, ContextClass contextClass) {
		val participation = participationContext.participation
		val participationClass = contextClass.participationClass
		return participation.conditions
			.filter[!isContainment] // containments are handled separately
			.filter[enforced]
			.filter[leftOperand.participationClass == participationClass]
			.filter [
				// Exclude conditions whose operands refer to participation
				// classes that are not involved in the current participation
				// context:
				rightOperands.map[it.participationClass].filterNull.forall [ operandParticipationClass |
					participationContext.classes.exists [
						it.participationClass == operandParticipationClass
					]
				]
			].map[participationConditionInitializer]
	}

	private def Function<TypeProvider, XExpression> getParticipationConditionInitializer(
		ParticipationCondition participationCondition) {
		return [ extension TypeProvider typeProvider |
			val operatorType = participationCondition.operator.jvmType.imported
			val enforceMethod = operatorType.findMethod(CONDITION_ENFORCE_METHOD)
			return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = participationCondition.newParticipationConditionOperator(typeProvider)
				feature = enforceMethod
				explicitOperationCall = true
			]
		]
	}

	private def newParticipationConditionOperator(ParticipationCondition participationCondition,
		extension TypeProvider typeProvider) {
		val operatorContext = new ParticipationConditionOperatorContext(typeProvider)
		val leftOperand = participationCondition.leftOperand
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operatorType = participationCondition.operator.jvmType
			constructor = operatorType.findConstructor(Object, List)
			explicitConstructorCall = true
			arguments += expressions(
				leftOperand.getOperandExpression(operatorContext),
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += participationCondition.rightOperands.map [
						getOperandExpression(operatorContext)
					]
				]
			)
		]
	}
}
