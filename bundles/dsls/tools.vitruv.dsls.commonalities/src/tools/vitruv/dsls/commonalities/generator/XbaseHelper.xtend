package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import java.util.Arrays
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XMemberFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory

@Utility
package class XbaseHelper {

	def static package join(XExpression first, XExpression second) {
		if (first === null) return second
		if (second === null) return first
		doJoin(first, second)
	}

	def private static dispatch XExpression doJoin(XExpression firstExpression, XBlockExpression secondBlock) {
		val secondExpressions = new ArrayList(secondBlock.expressions)
		secondBlock.expressions.clear()
		secondBlock.expressions += #[firstExpression] + secondExpressions
		secondBlock
	}

	def private static dispatch XExpression doJoin(XBlockExpression firstBlock, XBlockExpression secondBlock) {
		firstBlock.expressions += secondBlock.expressions
		firstBlock
	}

	def private static dispatch XExpression doJoin(XBlockExpression firstBlock, XExpression secondExpression) {
		firstBlock.expressions += secondExpression
		firstBlock
	}

	def private static dispatch XExpression doJoin(XExpression firstExpression, XExpression secondExpression) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += #[firstExpression, secondExpression]
		]
	}

	// Needed to convince the Xtend type system.
	def package static expressions(XExpression... expressions) {
		Arrays.asList(expressions)
	}

	def package static stringLiteral(String string) {
		XbaseFactory.eINSTANCE.createXStringLiteral => [
			value = string
		]
	}

	def package static memberFeatureCall(XExpression target) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = target
		]
	}

	def package static memberFeatureCall(XExpression target, JvmIdentifiableElement featureElement) {
		target.memberFeatureCall => [
			feature = featureElement
		]
	}

	def package static memberFeatureCall(JvmIdentifiableElement targetElement, JvmIdentifiableElement featureElement) {
		targetElement.featureCall.memberFeatureCall(featureElement)
	}

	def package static set(XMemberFeatureCall target, XMemberFeatureCall source) {
		target => [
			memberCallTarget = source.memberCallTarget
			feature = source.feature
		]
	}

	def package static featureCall(JvmIdentifiableElement featureElement) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = featureElement
		]
	}

	def package static newFeatureCall(XFeatureCall featureCall) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = featureCall.feature
			typeLiteral = featureCall.typeLiteral
			explicitOperationCall = featureCall.explicitOperationCall
		]
	}
}
