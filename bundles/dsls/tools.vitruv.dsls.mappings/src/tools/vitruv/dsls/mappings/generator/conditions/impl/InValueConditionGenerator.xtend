package tools.vitruv.dsls.mappings.generator.conditions.impl

import java.util.List
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.MultiValueConditionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ElementReplacedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.RemovedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGeneratorUtils.*
import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*

class InValueConditionGenerator extends MultiValueConditionGenerator {

	new(MultiValueCondition condition) {
		super(condition, MultiValueConditionOperator.IN)
	}
	
	/**
	 * Returns the child parameter of this  in-value condition
	 */
	def getChildParameter() {
		featureCondition.leftMappingParameter
	}

	/**
	 * Returns the parent parameter of this  in-value condition
	 */
	def getParentParameter() {
		featureCondition.feature.parameter
	}

	/**
	 * Returns the feature of this  in-value condition
	 */
	def getInFeature() {
		featureCondition.feature.feature
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

	override protected constructReactionTriggers(List<AbstractReactionTriggerGenerator> triggers) {
		if (featureCondition.isLeftSideMappingParameter) {
			val leftReference = featureCondition.leftMappingParameter.value
			// check if its a collection or just a normal element
			if (rightSide.feature.many) {
				if (condition.negated === null) {
					// only create reaction triggers for not negated in conditions
					triggers.add(new InsertedReactionTriggerGenerator(leftReference, rightSide))
				 triggers.add(new RemovedReactionTriggerGenerator(leftReference, rightSide))
				}
				triggers.add(new DeletedReactionTriggerGenerator(leftReference))
			} else {
				triggers.add(new ElementReplacedReactionTriggerGenerator(rightSide, leftReference));
			}
		}
		super.constructReactionTriggers(triggers)
	}

	override generateFeatureCondition(TypeProvider provider, XExpression variable) {
		// in relations have no condition logic
		null
	}

	override hasCorrespondenceInitialization() {
		featureCondition.isLeftSideMappingParameter && !negated
	}

	override generateCorrespondenceInitialization(TypeProvider typeProvider) {
		val feature = featureCondition.feature.feature as EReference
		if (feature.many) {
			// add to collection feature
			return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				explicitOperationCall = true
				feature = typeProvider.collectionAdd
				memberCallTarget = typeProvider.parameterFeatureCallGetter(featureCondition)
				memberCallArguments += typeProvider.parameter(childParameter)
			]
		} else {
			// just set 
			typeProvider.parameterFeatureCallSetter(featureCondition, typeProvider.parameter(childParameter))
		}
	}

}
