package tools.vitruv.dsls.mappings.generator.conditions

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.BoolValue
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameterReference
import tools.vitruv.dsls.mappings.mappingsLanguage.NullValue
import tools.vitruv.dsls.mappings.mappingsLanguage.NumberValue
import tools.vitruv.dsls.mappings.mappingsLanguage.StringValue
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*

class FeatureConditionGeneratorUtils {

	def static FeatureCondition getFeatureCondition(EObject childCondition) {
		childCondition.eContainer as FeatureCondition
	}

	def static getRightFeatureReference(FeatureCondition condition) {
		MirBaseFactory.eINSTANCE.createMetaclassFeatureReference => [
			metaclass = condition.feature.parameter.value.metaclass
			feature = condition.feature.feature
		]
	}

	def static isLeftSideMappingParameter(FeatureCondition condition) {
		condition.left instanceof MappingParameterReference
	}

	def static leftMappingParameter(FeatureCondition condition) {
		(condition.left as MappingParameterReference).parameter
	}

	def static parameter(TypeProvider typeProvider, MappingParameter parameter) {
		typeProvider.variable(parameter.value.name + '_')
	}

	private def static initMemberFeatureCall(TypeProvider typeProvider, FeatureCondition featureCondition) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			memberCallTarget = typeProvider.parameter(featureCondition.feature.parameter)
		]
	}

	def static parameterFeatureCallSetter(TypeProvider typeProvider, FeatureCondition featureCondition,
		XExpression argument) {
		val call = typeProvider.initMemberFeatureCall(featureCondition)
		call.feature = typeProvider.findMetaclassMethodSetter(featureCondition.feature.parameter.value.metaclass,
			featureCondition.feature.feature)
		call.memberCallArguments += argument
		call
	}

	def static parameterFeatureCallGetter(TypeProvider typeProvider, FeatureCondition featureCondition) {
		val call = typeProvider.initMemberFeatureCall(featureCondition)
		call.feature = typeProvider.findMetaclassMethodGetter(featureCondition.feature.parameter.value.metaclass,
			featureCondition.feature.feature)
		call
	}

	def static generateLeftFeatureConditionValue(TypeProvider typeProvider, FeatureCondition featureCondition) {
		val leftSide = featureCondition.left
		leftSide.initValue(typeProvider)
	}

	def private static dispatch initValue(NullValue value, TypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXNullLiteral
	}

	def private static dispatch initValue(StringValue value, TypeProvider typeProvider) {
		EcoreUtil.copy(value.value)
	}

	def private static dispatch initValue(NumberValue value, TypeProvider typeProvider) {
		EcoreUtil.copy(value.value)
	}

	def private static dispatch initValue(BoolValue value, TypeProvider typeProvider) {
		EcoreUtil.copy(value.value)
	}

	def private static dispatch initValue(MappingParameterReference value, TypeProvider typeProvider) {
		// val package = leftSide.metaclass.class.package.name
		val className = value.parameter.value.metaclass.class.name
		return XbaseFactory.eINSTANCE.createXTypeLiteral => [
			type = typeProvider.findTypeByName(className)
		]
	}
}
