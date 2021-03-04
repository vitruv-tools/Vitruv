package tools.vitruv.dsls.mappings.generator.conditions

import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.common.elements.MetaclassFeatureReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static extension tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGeneratorUtils.*

abstract class MultiValueConditionGenerator extends AbstractSingleSidedCondition<MultiValueCondition> implements FeatureConditionGenerator {

	var MultiValueConditionOperator operator
	@Accessors(PROTECTED_GETTER)
	var EObject leftSide
	@Accessors(PROTECTED_GETTER)
	var MetaclassFeatureReference rightSide
	@Accessors(PROTECTED_GETTER)
	var FeatureCondition featureCondition

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

	protected def isNegated() {
		condition.negated !== null
	}

	override protected constructReactionTriggers(List<AbstractReactionTriggerGenerator> triggers) {
			if (rightSide.feature instanceof EAttribute) {
		 		triggers.add(new AttributeReplacedReactionTriggerGenerator(rightSide))
		 }
	}

	override generate(UndecidedMatcherStatementBuilder builder) {
		// its a feature condition so this is not used
	}

}
