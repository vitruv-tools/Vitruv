package tools.vitruv.dsls.mappings.generator.conditions

import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference

import static extension tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGeneratorUtils.*

abstract class MultiValueConditionGenerator extends AbstractSingleSidedCondition<MultiValueCondition> implements FeatureConditionGenerator {

	protected MultiValueConditionOperator operator
	protected EObject leftSide
	protected MetaclassFeatureReference rightSide
	protected FeatureCondition featureCondition

	new(MultiValueCondition condition, MultiValueConditionOperator operatorType) {
		super(condition)
		operator = condition.operator
		if (operator != operatorType) {
			throw new IllegalStateException('''This generator expects the operatorType «operatorType» but is «operator»''')
		}
		featureCondition = getFeatureCondition(condition)
		leftSide = featureCondition.left
		rightSide = featureCondition.rightFeatureReference
	}

	override feasibleForGenerator(AbstractReactionTypeGenerator generator) {
		rightSide.metaclass == generator.metaclass
	}
	
	override protected constructReactionTriggers(List<AbstractReactionTypeGenerator> triggers) {
		if (rightSide.feature instanceof EAttribute) {
			triggers.add(new AttributeReplacedReactionGenerator(rightSide))
		}
	}
}
