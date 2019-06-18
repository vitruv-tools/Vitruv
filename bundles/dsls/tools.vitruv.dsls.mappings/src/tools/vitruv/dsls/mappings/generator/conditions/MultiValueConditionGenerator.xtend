package tools.vitruv.dsls.mappings.generator.conditions

import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.mappings.generator.trigger.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.trigger.InsertedInReactionGenerator
import tools.vitruv.dsls.mappings.generator.trigger.RemovedFromReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static tools.vitruv.dsls.mappings.generator.XExpressionParser.*
import tools.vitruv.dsls.mappings.generator.trigger.AttributeReplacedReactionGenerator

class MultiValueConditionGenerator extends AbstractSingleSidedCondition<MultiValueCondition> {

	private MultiValueConditionOperator operator
	private EObject leftSide
	private MetaclassFeatureReference rightSide

	new(MultiValueCondition condition) {
		super(condition)
		operator = condition.operator
		val featureCondition = FeatureConditionGeneratorUtils.getFeatureCondition(condition)
		leftSide = featureCondition.left
		rightSide = featureCondition.feature
	}
	
	override feasibleForGenerator(AbstractReactionTypeGenerator generator) {
		rightSide.metaclass == generator.metaclass
	}
		
	override protected constructReactionTriggers(List<AbstractReactionTypeGenerator> triggers) {
		if (operator == MultiValueConditionOperator.IN) {
			if(leftSide instanceof MetaclassReference){
				triggers.add(new InsertedInReactionGenerator(leftSide, rightSide))
				triggers.add(new RemovedFromReactionGenerator(leftSide, rightSide))
			}
		}
		triggers.add(new AttributeReplacedReactionGenerator(rightSide))
	}

	override generate(UndecidedMatcherStatementBuilder builder) {
		var negated = false
		if (condition.negated !== null) {
			// negated
			negated = true
		}
		if (operator == MultiValueConditionOperator.EQUALS) {
			generateEquals(builder, leftSide, rightSide, negated)
		} else if (operator == MultiValueConditionOperator.IN) {
			generateIn(builder, leftSide, rightSide, negated)
		}
	}

	private def generateIn(UndecidedMatcherStatementBuilder builder, EObject leftSide,
		MetaclassFeatureReference rightSide, boolean negated) {
		// builder.vall('''in«leftSide»''').retrieve(rightSide.metaclass).correspondingTo('''«rightSide»''')
		builder.check([ typeProvider |
			parseExpression('5 == 10')
		])
	}

	private def generateEquals(UndecidedMatcherStatementBuilder builder, EObject leftSide,
		MetaclassFeatureReference rightSide, boolean negated) {
		builder.check([ typeProvider |
			parseExpression('5 == 10')
		])
	// builder.vall('''equals«leftSide»''').retrieve(rightSide.metaclass).correspondingTo('''«rightSide»''')		
	/*builder.check([
	 * 	
	 ])*/
	}
	

	

	

}
