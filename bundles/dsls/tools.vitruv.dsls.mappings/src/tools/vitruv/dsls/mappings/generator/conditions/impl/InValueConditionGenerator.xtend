package tools.vitruv.dsls.mappings.generator.conditions.impl

import java.util.List
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.MultiValueConditionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
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
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameterReference

class InValueConditionGenerator extends MultiValueConditionGenerator {

	new(MultiValueCondition condition) {
		super(condition, MultiValueConditionOperator.IN)
	}

	override feasibleForGenerator(AbstractReactionTypeGenerator generator) {
		if (!generator.usesNewValue) {
			// In-Statements only work with reactions that produce two values
			return false
		}
		super.feasibleForGenerator(generator)
	}

	override feasibleForParameter(MappingParameter parameter) {
		// for in statements the parameter has to correspond to the left side
		if (featureCondition.isLeftSideMappingParameter) {
			// only makes sense for mapping parameters
			val leftParameter = featureCondition.leftMappingParameter
			return leftParameter == parameter
		}
		false
	}

	override protected constructReactionTriggers(List<AbstractReactionTypeGenerator> triggers) {
		if (featureCondition.isLeftSideMappingParameter) {
			val leftReference = featureCondition.leftMappingParameter.value
			// check if its a collection or just a normal element
			if (rightSide.feature.many) {
				if (condition.negated === null) {
					// only create reaction triggers for not negated in conditions
					triggers.add(new InsertedReactionGenerator(leftReference, rightSide))
				// triggers.add(new RemovedReactionGenerator(leftSide, rightSide))
				}
				triggers.add(new DeletedReactionGenerator(leftReference))
			} else {
				triggers.add(new ElementReplacedReactionGenerator(rightSide, leftReference));
			}
		}
		super.constructReactionTriggers(triggers)
	}

	override generateFeatureCondition(RoutineTypeProvider provider, boolean usesNewValue) {
		var negated = condition.negated !== null
		if (usesNewValue) {
			provider.generateIn(negated)
		}
	}

	private def generateIn(RoutineTypeProvider typeProvider, boolean negated) {
		val parameter = featureCondition.feature.parameter
		/*XbaseFactory.eINSTANCE.createXClosure => [
		 * 	expression = XbaseFactory.eINSTANCE.createXBooleanLiteral
		 ]*/
		XbaseFactory.eINSTANCE.createXBooleanLiteral
	}

	override generate(UndecidedMatcherStatementBuilder builder) {
		// its a feature condition so this is not used
	}

}
