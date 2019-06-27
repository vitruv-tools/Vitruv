package tools.vitruv.dsls.mappings.generator.conditions

import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static tools.vitruv.dsls.mappings.generator.XExpressionParser.*
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.RemovedReactionGenerator

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
				triggers.add(new InsertedReactionGenerator(leftSide, rightSide))
				triggers.add(new RemovedReactionGenerator(leftSide, rightSide))
			}
		}
		if(rightSide.feature instanceof EAttribute){
			triggers.add(new AttributeReplacedReactionGenerator(rightSide))			
		}
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
			parseExpression('')
		])
	}

	private def generateEquals(UndecidedMatcherStatementBuilder builder, EObject leftSide,
		MetaclassFeatureReference rightSide, boolean negated) {
		builder.check([ typeProvider |
			parseExpression('')
		])
	// builder.vall('''equals«leftSide»''').retrieve(rightSide.metaclass).correspondingTo('''«rightSide»''')		
	/*builder.check([
	 * 	
	 ])*/
	}
	

	

	

}
