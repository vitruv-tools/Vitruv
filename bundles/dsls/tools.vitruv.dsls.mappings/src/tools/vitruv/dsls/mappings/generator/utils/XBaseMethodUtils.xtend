package tools.vitruv.dsls.mappings.generator.utils

import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*

class XBaseMethodUtils {

	static def binaryOperationChain(TypeProvider provider,JvmIdentifiableElement operation, XExpression... expressions) {
		if (expressions.empty) {
			return XbaseFactory.eINSTANCE.createXBooleanLiteral => [
				isTrue = true
			]
		}
		var leftExpression = expressions.get(0)
		for (expression : expressions.drop(1)) {
			val andExpression = XbaseFactory.eINSTANCE.createXBinaryOperation
			andExpression.leftOperand = leftExpression
			andExpression.rightOperand = expression
			andExpression.feature = operation
			leftExpression = andExpression
		}
		return leftExpression
	}
	
	static def andChain(TypeProvider provider, XExpression... expressions) {
		provider.binaryOperationChain(provider.and, expressions)
	}
	
	static def orChain(TypeProvider provider, XExpression... expressions) {
		provider.binaryOperationChain(provider.or, expressions)
	}

	static def findTypeReference(TypeProvider provider, MappingParameter parameter) {
		provider.jvmTypeReferenceBuilder.typeRef(provider.findType(parameter))
	}

	static def findType(TypeProvider provider, MappingParameter parameter) {
		val package = parameter.value.metaclass.instanceTypeName
		provider.findTypeByName(package) as JvmDeclaredType
	}

	static def optionalNotEmpty(TypeProvider provider, XExpression variable) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			memberCallTarget = variable
			feature = provider.optionalIsPresent
		]
	}

	static def optionalGet(TypeProvider provider, XExpression variable) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			memberCallTarget = variable
			feature = provider.optionalGet
		]
	}

	static def notNull(TypeProvider provider, String variable) {
		provider.notNull(provider.variable(variable))
	}

	static def notNull(TypeProvider provider, XExpression variable) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = variable
			feature = XBaseMethodFinder.tripleNotEquals(provider)
			rightOperand = XbaseFactory.eINSTANCE.createXNullLiteral
		]
	}
}
