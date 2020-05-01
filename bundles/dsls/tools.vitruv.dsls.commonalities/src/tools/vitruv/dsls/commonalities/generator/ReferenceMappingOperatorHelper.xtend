package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.List
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.LiteralOperand
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperand
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperator
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.IReferenceMappingOperator
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*

@GenerationScoped
package class ReferenceMappingOperatorHelper extends ReactionsGenerationHelper {

	@Inject extension OperandHelper operandHelper

	package new() {
	}

	def callConstructor(ReferenceMappingOperator operator, Iterable<ReferenceMappingOperand> operands,
		extension TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operatorType = operator.jvmType.imported
			constructor = operatorType.findConstructor(ReactionExecutionState, List)
			explicitConstructorCall = true
			arguments += expressions(
				executionState,
				XbaseFactory.eINSTANCE.createXListLiteral => [
					// Note: We only pass values for the literal operands to the operator.
					elements += operands.filter(LiteralOperand).map[getOperandExpression(typeProvider)].filterNull
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
}
