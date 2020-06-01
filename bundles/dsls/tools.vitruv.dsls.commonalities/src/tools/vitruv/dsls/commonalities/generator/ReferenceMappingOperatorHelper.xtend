package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.LiteralOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperand
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperator
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.AttributeReferenceHelper
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.IReferenceMappingOperator
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

@GenerationScoped
package class ReferenceMappingOperatorHelper extends ReactionsGenerationHelper {

	private static class ReferenceMappingOperatorContext implements OperatorContext {

		val extension TypeProvider typeProvider

		new(TypeProvider typeProvider) {
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

	def callConstructor(ReferenceMappingOperator operator, Iterable<ReferenceMappingOperand> operands,
		extension TypeProvider typeProvider) {
		val operatorContext = new ReferenceMappingOperatorContext(typeProvider)
		return XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operatorType = operator.jvmType.imported
			constructor = operatorType.findConstructor(ReactionExecutionState, List)
			explicitConstructorCall = true
			arguments += expressions(
				executionState,
				XbaseFactory.eINSTANCE.createXListLiteral => [
					// We only pass the literal operands to the operator:
					elements += operands.filter(LiteralOperand).getOperandExpressions(operatorContext)
				]
			)
		]
	}

	private def callOperatorMethod(ReferenceMappingOperator operator, JvmOperation method,
		Iterable<ReferenceMappingOperand> operands, extension TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = operator.callConstructor(operands, typeProvider)
			feature = method
			explicitOperationCall = true
		]
	}

	def callGetContainedObjects(ReferenceMappingOperator operator, Iterable<ReferenceMappingOperand> operands,
		XExpression containerObject, extension TypeProvider typeProvider) {
		val method = typeProvider.findMethod(IReferenceMappingOperator, "getContainedObjects")
		return operator.callOperatorMethod(method, operands, typeProvider) => [
			memberCallArguments += containerObject
		]
	}

	def callGetContainer(ReferenceMappingOperator operator, Iterable<ReferenceMappingOperand> operands,
		XExpression containedObject, extension TypeProvider typeProvider) {
		val method = typeProvider.findMethod(IReferenceMappingOperator, "getContainer")
		return operator.callOperatorMethod(method, operands, typeProvider) => [
			memberCallArguments += containedObject
		]
	}

	def callIsContained(ReferenceMappingOperator operator, Iterable<ReferenceMappingOperand> operands,
		XExpression containerObject, XExpression containedObject, extension TypeProvider typeProvider) {
		val method = typeProvider.findMethod(IReferenceMappingOperator, "isContained")
		return operator.callOperatorMethod(method, operands, typeProvider) => [
			memberCallArguments += containerObject
			memberCallArguments += containedObject
		]
	}

	def callInsert(ReferenceMappingOperator operator, Iterable<ReferenceMappingOperand> operands,
		XExpression containerObject, XExpression objectToInsert, extension TypeProvider typeProvider) {
		val method = typeProvider.findMethod(IReferenceMappingOperator, "insert")
		return operator.callOperatorMethod(method, operands, typeProvider) => [
			memberCallArguments += containerObject
			memberCallArguments += objectToInsert
		]
	}

	def callGetPotentiallyContainedIntermediates(ReferenceMappingOperator operator,
		Iterable<ReferenceMappingOperand> operands, XExpression containerObject, EClass intermediateType,
		extension TypeProvider typeProvider) {
		val attributeReferenceHelperType = typeProvider.findDeclaredType(AttributeReferenceHelper).imported
		val method = attributeReferenceHelperType.findMethod("getPotentiallyContainedIntermediates")
		val intermediateJvmType = typeProvider.findTypeByName(intermediateType.javaClassName)
		return attributeReferenceHelperType.memberFeatureCall(method) => [
			staticWithDeclaringType = true
			typeArguments += jvmTypeReferenceBuilder.typeRef(intermediateJvmType)
			memberCallArguments += expressions(
				operator.callConstructor(operands, typeProvider),
				containerObject,
				correspondenceModel,
				XbaseFactory.eINSTANCE.createXTypeLiteral => [
					type = intermediateJvmType
				]
			)
		]
	}

	def callGetPotentialContainerIntermediate(ReferenceMappingOperator operator,
		Iterable<ReferenceMappingOperand> operands, XExpression containedObject, EClass intermediateType,
		extension TypeProvider typeProvider) {
		val attributeReferenceHelperType = typeProvider.findDeclaredType(AttributeReferenceHelper).imported
		val method = attributeReferenceHelperType.findMethod("getPotentialContainerIntermediate")
		val intermediateJvmType = typeProvider.findTypeByName(intermediateType.javaClassName)
		return attributeReferenceHelperType.memberFeatureCall(method) => [
			staticWithDeclaringType = true
			typeArguments += jvmTypeReferenceBuilder.typeRef(intermediateJvmType)
			memberCallArguments += expressions(
				operator.callConstructor(operands, typeProvider),
				containedObject,
				correspondenceModel,
				XbaseFactory.eINSTANCE.createXTypeLiteral => [
					type = intermediateJvmType
				]
			)
		]
	}
}
