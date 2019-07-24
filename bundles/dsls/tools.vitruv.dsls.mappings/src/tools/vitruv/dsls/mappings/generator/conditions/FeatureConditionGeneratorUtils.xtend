package tools.vitruv.dsls.mappings.generator.conditions

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.BoolValue
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameterReference
import tools.vitruv.dsls.mappings.mappingsLanguage.NullValue
import tools.vitruv.dsls.mappings.mappingsLanguage.NumberValue
import tools.vitruv.dsls.mappings.mappingsLanguage.StringValue
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
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

	def static parameter(RoutineTypeProvider typeProvider, MappingParameter parameter) {
		typeProvider.variable(parameter.value.name)
	}

	def static parameterFeatureCall(RoutineTypeProvider typeProvider, FeatureCondition featureCondition) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			implicitReceiver = typeProvider.parameter(featureCondition.feature.parameter)
			feature = typeProvider.findMetaclassMethod(featureCondition.feature.parameter.value.metaclass,
				featureCondition.feature.feature)
		]
	}

	def static generateLeftFeatureConditionValue(RoutineTypeProvider typeProvider, FeatureCondition featureCondition) {
		val leftSide = featureCondition.left
		if (leftSide instanceof NullValue) {
			return XbaseFactory.eINSTANCE.createXNullLiteral
		} else if (leftSide instanceof StringValue) {
			return EcoreUtil.copy(leftSide.value)
		} else if (leftSide instanceof NumberValue) {
			return EcoreUtil.copy(leftSide.value)
		} else if (leftSide instanceof BoolValue) {
			return EcoreUtil.copy(leftSide.value)
		} else if (leftSide instanceof MappingParameterReference) {
			// val package = leftSide.metaclass.class.package.name
			val className = leftSide.parameter.value.metaclass.class.name
			return XbaseFactory.eINSTANCE.createXTypeLiteral => [
				type = typeProvider.findTypeByName(className)
			]
		}
	}

}
