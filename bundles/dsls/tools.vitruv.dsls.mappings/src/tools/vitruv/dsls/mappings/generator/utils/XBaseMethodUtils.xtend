package tools.vitruv.dsls.mappings.generator.utils

import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import java.util.Optional
import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*

class XBaseMethodUtils {

	public static def andChain(RoutineTypeProvider provider, XExpression... expressions) {
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
			andExpression.feature = XBaseMethodFinder.and(provider)
			leftExpression = andExpression
		}
		return leftExpression
	}

	public static def findTypeReference(RoutineTypeProvider provider, MappingParameter parameter) {

		provider.jvmTypeReferenceBuilder.typeRef(provider.findType(parameter))
	}

	public static def findType(RoutineTypeProvider provider, MappingParameter parameter) {
		val package = parameter.value.metaclass.instanceTypeName
		provider.findTypeByName(package) as JvmDeclaredType
	}

	public static def optionalNotEmpty(RoutineTypeProvider provider, XExpression variable) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			memberCallTarget = variable
			feature = provider.optionalIsPresent
		]
	}

	public static def optionalGet(RoutineTypeProvider provider, XExpression variable) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			memberCallTarget = variable
			feature = provider.optionalGet
		]
	}

	public static def notNull(RoutineTypeProvider provider, String variable) {
		provider.notNull(provider.variable(variable))
	}

	public static def notNull(RoutineTypeProvider provider, XExpression variable) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = variable
			feature = XBaseMethodFinder.tripleNotEquals(provider)
			rightOperand = XbaseFactory.eINSTANCE.createXNullLiteral
		]
	}
}
