package tools.vitruv.dsls.mappings.generator.conditions.impl

import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.MultiValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static extension tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGeneratorUtils.*
import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*
import org.eclipse.xtext.xbase.XExpression

class EqualsValueConditionGenerator extends MultiValueConditionGenerator {

	new(MultiValueCondition condition) {
		super(condition, MultiValueConditionOperator.EQUALS)
	}

	override feasibleForParameter(MappingParameter parameter) {
		featureCondition.feature.parameter == parameter
	}

	override generateFeatureCondition(RoutineTypeProvider typeProvider, XExpression variable) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = typeProvider.generateLeftFeatureConditionValue(featureCondition)
			rightOperand = typeProvider.generateRightSideCall(variable)
			if (negated) {
				feature = typeProvider.tripleNotEquals
			} else {
				feature = typeProvider.tripleEquals
			}
		]
	}

	override generateCorrespondenceInitialization(RoutineTypeProvider typeProvider) {
		if (!negated) {
			return XbaseFactory.eINSTANCE.createXAssignment => [
				assignable = typeProvider.parameterFeatureCall(featureCondition)
				value = typeProvider.generateLeftFeatureConditionValue(featureCondition)
			]
		}
		null
	}

	private def generateRightSideCall(RoutineTypeProvider typeProvider, XExpression variable) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			memberCallTarget = variable
			feature = typeProvider.findMetaclassMethod(rightSide)
		]
	}

	override generate(UndecidedMatcherStatementBuilder builder) {
		// its a feature condition so this is not used
	}

}
