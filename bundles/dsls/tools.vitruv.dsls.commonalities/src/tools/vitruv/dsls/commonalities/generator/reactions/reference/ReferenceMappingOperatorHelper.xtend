package tools.vitruv.dsls.commonalities.generator.reactions.reference

import com.google.inject.Inject
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.operator.OperandHelper
import tools.vitruv.dsls.commonalities.generator.reactions.operator.OperatorContext
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.AttributeReferenceHelper
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.IReferenceMappingOperator

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ReferenceMappingOperatorHelper extends ReactionsGenerationHelper {

	static class ReferenceMappingOperatorContext implements OperatorContext {

		val extension TypeProvider typeProvider

		new(TypeProvider typeProvider) {
			checkNotNull(typeProvider, "typeProvider is null")
			this.typeProvider = typeProvider
		}

		override getTypeProvider() {
			return typeProvider
		}

		private def unsupportedOperationException() {
			return new UnsupportedOperationException("Unsupported in reference mapping context!")
		}

		override passParticipationAttributeValues() {
			throw unsupportedOperationException()
		}

		override passCommonalityAttributeValues() {
			throw unsupportedOperationException()
		}

		override getIntermediate() {
			throw unsupportedOperationException()
		}

		override getParticipationObject(ParticipationClass participationClass) {
			throw unsupportedOperationException()
		}
	}

	@Inject extension OperandHelper operandHelper

	package new() {
	}

	def constructOperator(OperatorReferenceMapping mapping, ReferenceMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		return XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operatorType = mapping.operator.jvmType.imported
			// Assert: There is only one constructor and it matches the passed operands.
			// TODO Ensure this via validation.
			constructor = operatorType.findConstructor()
			explicitConstructorCall = true
			arguments += executionState
			// Does not include the attribute operands:
			arguments += mapping.passedOperands.getOperandExpressions(operatorContext)
		]
	}

	private def callOperatorMethod(OperatorReferenceMapping mapping, JvmOperation method,
		ReferenceMappingOperatorContext operatorContext) {
		return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = mapping.constructOperator(operatorContext)
			feature = method
			explicitOperationCall = true
		]
	}

	def callGetContainedObjects(OperatorReferenceMapping mapping, XExpression containerObject,
		ReferenceMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val method = typeProvider.findMethod(IReferenceMappingOperator, 'getContainedObjects')
		return mapping.callOperatorMethod(method, operatorContext) => [
			memberCallArguments += containerObject
		]
	}

	def callGetContainer(OperatorReferenceMapping mapping, XExpression containedObject,
		ReferenceMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val method = typeProvider.findMethod(IReferenceMappingOperator, 'getContainer')
		return mapping.callOperatorMethod(method, operatorContext) => [
			memberCallArguments += containedObject
		]
	}

	def callIsContained(OperatorReferenceMapping mapping, XExpression containerObject, XExpression containedObject,
		ReferenceMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val method = typeProvider.findMethod(IReferenceMappingOperator, 'isContained')
		return mapping.callOperatorMethod(method, operatorContext) => [
			memberCallArguments += containerObject
			memberCallArguments += containedObject
		]
	}

	def callInsert(OperatorReferenceMapping mapping, XExpression containerObject, XExpression objectToInsert,
		ReferenceMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val method = typeProvider.findMethod(IReferenceMappingOperator, 'insert')
		return mapping.callOperatorMethod(method, operatorContext) => [
			memberCallArguments += containerObject
			memberCallArguments += objectToInsert
		]
	}

	def callGetPotentiallyContainedIntermediates(OperatorReferenceMapping mapping, XExpression containerObject,
		EClass intermediateType, ReferenceMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val attributeReferenceHelperType = typeProvider.findDeclaredType(AttributeReferenceHelper).imported
		val method = attributeReferenceHelperType.findMethod('getPotentiallyContainedIntermediates')
		val intermediateJvmType = typeProvider.findTypeByName(intermediateType.javaClassName)
		return attributeReferenceHelperType.memberFeatureCall(method) => [
			staticWithDeclaringType = true
			typeArguments += jvmTypeReferenceBuilder.typeRef(intermediateJvmType)
			memberCallArguments += expressions(
				mapping.constructOperator(operatorContext),
				containerObject,
				correspondenceModel,
				XbaseFactory.eINSTANCE.createXTypeLiteral => [
					type = intermediateJvmType
				]
			)
		]
	}

	def callGetPotentialContainerIntermediate(OperatorReferenceMapping mapping, XExpression containedObject,
		EClass intermediateType, ReferenceMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		val attributeReferenceHelperType = typeProvider.findDeclaredType(AttributeReferenceHelper).imported
		val method = attributeReferenceHelperType.findMethod('getPotentialContainerIntermediate')
		val intermediateJvmType = typeProvider.findTypeByName(intermediateType.javaClassName)
		return attributeReferenceHelperType.memberFeatureCall(method) => [
			staticWithDeclaringType = true
			typeArguments += jvmTypeReferenceBuilder.typeRef(intermediateJvmType)
			memberCallArguments += expressions(
				mapping.constructOperator(operatorContext),
				containedObject,
				correspondenceModel,
				XbaseFactory.eINSTANCE.createXTypeLiteral => [
					type = intermediateJvmType
				]
			)
		]
	}
}
