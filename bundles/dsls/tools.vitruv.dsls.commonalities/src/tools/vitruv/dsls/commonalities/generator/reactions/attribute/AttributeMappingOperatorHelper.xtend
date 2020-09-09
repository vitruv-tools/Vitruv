package tools.vitruv.dsls.commonalities.generator.reactions.attribute

import com.google.inject.Inject
import java.util.function.Function
import java.util.function.Supplier
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.operator.OperandHelper
import tools.vitruv.dsls.commonalities.generator.reactions.operator.OperatorContext
import tools.vitruv.dsls.commonalities.language.OperatorAttributeMapping
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.IAttributeMappingOperator

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class AttributeMappingOperatorHelper extends ReactionsGenerationHelper {

	static class AttributeMappingOperatorContext implements OperatorContext {

		val extension TypeProvider typeProvider
		val Supplier<XExpression> intermediate
		val Function<ParticipationClass, XExpression> participationClassToObject

		new(TypeProvider typeProvider, Supplier<XExpression> intermediate,
			Function<ParticipationClass, XExpression> participationClassToObject) {
			checkNotNull(typeProvider, "typeProvider is null")
			checkNotNull(intermediate, "intermediate is null")
			checkNotNull(participationClassToObject, "participationClassToObject is null")
			this.typeProvider = typeProvider
			this.intermediate = intermediate
			this.participationClassToObject = participationClassToObject
		}

		override getTypeProvider() {
			return typeProvider
		}

		private def unsupportedOperationException() {
			return new UnsupportedOperationException("Unsupported in attribute mapping context!")
		}

		override passParticipationAttributeValues() {
			throw unsupportedOperationException()
		}

		override passCommonalityAttributeValues() {
			// Operators are not supposed to modify any objects, so we only pass attribute values.
			return true
		}

		override getIntermediate() {
			return intermediate.get
		}

		override getParticipationObject(ParticipationClass participationClass) {
			return participationClassToObject.apply(participationClass)
		}
	}

	@Inject extension OperandHelper operandHelper

	package new() {
	}

	def constructOperator(OperatorAttributeMapping mapping, AttributeMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		return XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operatorType = mapping.operator.jvmType.imported
			// Assert: There is only one constructor and it matches the passed operands.
			// TODO Ensure this via validation.
			constructor = operatorType.findConstructor()
			explicitConstructorCall = true
			arguments += executionState
			// Note: We only pass the operands that are common to both application directions.
			arguments += mapping.commonOperands.getOperandExpressions(operatorContext)
		]
	}

	private def callOperatorMethod(OperatorAttributeMapping mapping, JvmOperation method,
		AttributeMappingOperatorContext operatorContext) {
		return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = mapping.constructOperator(operatorContext)
			feature = method
			explicitOperationCall = true
		]
	}

	def applyTowardsCommonality(OperatorAttributeMapping mapping, XExpression participationAttributeValue,
		AttributeMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val method = typeProvider.findMethod(IAttributeMappingOperator, 'applyTowardsCommonality')
		return mapping.callOperatorMethod(method, operatorContext) => [
			memberCallArguments += participationAttributeValue
		]
	}

	def applyTowardsParticipation(OperatorAttributeMapping mapping, XExpression commonalityAttributeValue,
		AttributeMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val method = typeProvider.findMethod(IAttributeMappingOperator, 'applyTowardsParticipation')
		return mapping.callOperatorMethod(method, operatorContext) => [
			memberCallArguments += commonalityAttributeValue
		]
	}
}
