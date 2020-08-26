package tools.vitruv.dsls.commonalities.generator.reactions.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import java.util.Arrays
import java.util.Optional
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XMemberFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*

@Utility
class XbaseHelper {

	static def join(XExpression first, XExpression second) {
		if (first === null) return second
		if (second === null) return first
		doJoin(first, second)
	}

	private static def dispatch XExpression doJoin(XExpression firstExpression, XBlockExpression secondBlock) {
		val secondExpressions = new ArrayList(secondBlock.expressions)
		secondBlock.expressions.clear()
		secondBlock.expressions += #[firstExpression] + secondExpressions
		secondBlock
	}

	private static def dispatch XExpression doJoin(XBlockExpression firstBlock, XBlockExpression secondBlock) {
		firstBlock.expressions += secondBlock.expressions
		firstBlock
	}

	private static def dispatch XExpression doJoin(XBlockExpression firstBlock, XExpression secondExpression) {
		firstBlock.expressions += secondExpression
		firstBlock
	}

	private static def dispatch XExpression doJoin(XExpression firstExpression, XExpression secondExpression) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += #[firstExpression, secondExpression]
		]
	}

	// Needed to convince the Xtend type system.
	static def expressions(XExpression... expressions) {
		Arrays.asList(expressions)
	}

	static def stringLiteral(String string) {
		if (string === null) {
			return nullLiteral
		} else {
			XbaseFactory.eINSTANCE.createXStringLiteral => [
				value = string
			]
		}
	}

	static def booleanLiteral(boolean value) {
		XbaseFactory.eINSTANCE.createXBooleanLiteral => [
			it.isTrue = value
		]
	}

	static def memberFeatureCall(XExpression target) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = target
		]
	}

	static def memberFeatureCall(JvmIdentifiableElement targetElement) {
		targetElement.featureCall.memberFeatureCall
	}

	static def memberFeatureCall(XExpression target, JvmIdentifiableElement featureElement) {
		target.memberFeatureCall => [
			feature = featureElement
		]
	}

	static def memberFeatureCall(JvmIdentifiableElement targetElement, JvmIdentifiableElement featureElement) {
		targetElement.featureCall.memberFeatureCall(featureElement)
	}

	static def set(XMemberFeatureCall target, XMemberFeatureCall source) {
		target => [
			memberCallTarget = source.memberCallTarget
			feature = source.feature
		]
	}

	static def featureCall(JvmIdentifiableElement featureElement) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = featureElement
		]
	}

	static def <T extends XExpression> T copy(T expression) {
		return EcoreUtil.copy(expression)
	}

	static def noArgsConstructorCall(JvmDeclaredType type) {
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			constructor = type.findNoArgsConstructor
			explicitConstructorCall = true
		]
	}

	static def nullLiteral() {
		XbaseFactory.eINSTANCE.createXNullLiteral
	}

	static def negated(XExpression operand, IJvmTypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXUnaryOperation => [
			feature = typeProvider.findMethod(BooleanExtensions, 'operator_not')
			it.operand = operand
		]
	}

	static def or(XExpression leftOperand, XExpression rightOperand, IJvmTypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBinaryOperation => [
			it.leftOperand = leftOperand
			feature = typeProvider.findMethod(BooleanExtensions, 'operator_or')
			it.rightOperand = rightOperand
		]
	}

	static def and(XExpression leftOperand, XExpression rightOperand, IJvmTypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBinaryOperation => [
			it.leftOperand = leftOperand
			feature = typeProvider.findMethod(BooleanExtensions, 'operator_and')
			it.rightOperand = rightOperand
		]
	}

	static def equals(XExpression leftOperand, XExpression rightOperand, IJvmTypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBinaryOperation => [
			it.leftOperand = leftOperand
			feature = typeProvider.findMethod(ObjectExtensions, 'operator_equals')
			it.rightOperand = rightOperand
		]
	}

	static def notEquals(XExpression leftOperand, XExpression rightOperand, IJvmTypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBinaryOperation => [
			it.leftOperand = leftOperand
			feature = typeProvider.findMethod(ObjectExtensions, 'operator_notEquals')
			it.rightOperand = rightOperand
		]
	}

	static def identityEquals(XExpression leftOperand, XExpression rightOperand, IJvmTypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBinaryOperation => [
			it.leftOperand = leftOperand
			feature = typeProvider.findMethod(ObjectExtensions, 'operator_tripleEquals')
			it.rightOperand = rightOperand
		]
	}

	static def notIdentityEquals(XExpression leftOperand, XExpression rightOperand, IJvmTypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBinaryOperation => [
			it.leftOperand = leftOperand
			feature = typeProvider.findMethod(ObjectExtensions, 'operator_tripleNotEquals')
			it.rightOperand = rightOperand
		]
	}

	static def equalsNull(XExpression leftOperand, IJvmTypeProvider typeProvider) {
		return leftOperand.identityEquals(nullLiteral, typeProvider)
	}

	static def notEqualsNull(XExpression leftOperand, IJvmTypeProvider typeProvider) {
		return leftOperand.notIdentityEquals(nullLiteral, typeProvider)
	}

	static def isInstanceOf(XExpression leftOperand, JvmTypeReference type) {
		return XbaseFactory.eINSTANCE.createXInstanceOfExpression => [
			expression = leftOperand
			it.type = type
		]
	}

	static def optionalIsPresent(XExpression optional, IJvmTypeProvider typeProvider) {
		return optional.memberFeatureCall => [
			feature = typeProvider.findDeclaredType(Optional).findMethod('isPresent')
			explicitOperationCall = true
		]
	}

	static def optionalGetOrNull(XExpression optional, IJvmTypeProvider typeProvider) {
		return optional.memberFeatureCall => [
			feature = typeProvider.findDeclaredType(Optional).findMethod('orElse')
			memberCallArguments += nullLiteral
			explicitOperationCall = true
		]
	}

	static def optionalGet(XExpression optional, IJvmTypeProvider typeProvider) {
		return optional.memberFeatureCall => [
			feature = typeProvider.findDeclaredType(Optional).findMethod('get')
		]
	}
}
