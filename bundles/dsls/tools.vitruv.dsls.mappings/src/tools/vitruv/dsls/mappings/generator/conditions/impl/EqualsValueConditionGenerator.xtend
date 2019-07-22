package tools.vitruv.dsls.mappings.generator.conditions.impl

import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.MultiValueConditionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ElementReplacedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static extension tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGeneratorUtils.*
import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*

class EqualsValueConditionGenerator extends MultiValueConditionGenerator {

	new(MultiValueCondition condition) {
		super(condition, MultiValueConditionOperator.EQUALS)
	}

	override feasibleForParameter(MappingParameter parameter) {
		featureCondition.feature.parameter == parameter
	}

	override generateFeatureCondition(RoutineTypeProvider provider, boolean usesNewValue) {
		var negated = condition.negated !== null
		provider.generateEquals(negated, usesNewValue)
	}

	private def generateEquals(RoutineTypeProvider typeProvider, boolean negated, boolean usesNewValue) {
		// builder.builder.correspondingMethodParameter()
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = typeProvider.generateLeftFeatureConditionValue(featureCondition)
			rightOperand = typeProvider.generateRightSideCall(usesNewValue)
			if (negated) {
				feature = typeProvider.tripleNotEquals
			} else {
				feature = typeProvider.tripleEquals
			}
		]
	}

	private def generateRightSideCall(RoutineTypeProvider typeProvider, boolean usesNewValue) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			if (usesNewValue) {
				// when we have an additional newValue, our feature is the newValue and not the affectedEobject
				memberCallTarget = typeProvider.newValue
			} else {
				memberCallTarget = typeProvider.affectedEObject
			}
			feature = typeProvider.findMetaclassMethod(rightSide)
		]
	}

	override generate(UndecidedMatcherStatementBuilder builder) {
		// its a feature condition so this is not used
	}

}
