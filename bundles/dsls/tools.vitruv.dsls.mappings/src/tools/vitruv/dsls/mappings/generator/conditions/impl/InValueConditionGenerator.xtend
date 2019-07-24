package tools.vitruv.dsls.mappings.generator.conditions.impl

import java.util.List
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.MultiValueConditionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ElementReplacedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static extension tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGeneratorUtils.*
import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*

class InValueConditionGenerator extends MultiValueConditionGenerator {

	new(MultiValueCondition condition) {
		super(condition, MultiValueConditionOperator.IN)
	}

	public def getChildParameter() {
		featureCondition.leftMappingParameter
	}

	public def getParentParameter() {
		featureCondition.feature.parameter
	}

	public def getInFeature() {
		featureCondition.feature.feature
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

	override generateFeatureCondition(RoutineTypeProvider provider, XExpression variable) {
		// in relations have no condition logic
		null
	}

	override generateCorrespondenceInitialization(RoutineTypeProvider typeProvider) {
		if (!negated && featureCondition.isLeftSideMappingParameter) {
			val feature = featureCondition.feature.feature as EReference
			if (feature.many) {
				// add to collection feature
				return XbaseFactory.eINSTANCE.createXFeatureCall => [
					feature = typeProvider.collectionAdd
					featureCallArguments += typeProvider.parameterFeatureCall(featureCondition)
					featureCallArguments += typeProvider.parameter(childParameter)
				]
			} else {
				// just set 
				return XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = typeProvider.parameterFeatureCall(featureCondition)
					value = typeProvider.parameter(childParameter)
				]
			}
		}
		null
	}

	override generate(UndecidedMatcherStatementBuilder builder) {
		// its a feature condition so this is not used
	}

}
