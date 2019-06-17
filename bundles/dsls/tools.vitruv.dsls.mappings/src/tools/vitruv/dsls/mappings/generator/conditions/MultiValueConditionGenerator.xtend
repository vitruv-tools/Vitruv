package tools.vitruv.dsls.mappings.generator.conditions

import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator

class MultiValueConditionGenerator extends AbstractSingleSidedCondition<MultiValueCondition>{
	
	new(MultiValueCondition condition) {
		super(condition)
	}
	
	override generate(UndecidedMatcherStatementBuilder builder) {
		var negated = false
		if(condition.negated!==null){
			//negated
			negated = true
		}
		val operator = condition.operator
		if(operator == MultiValueConditionOperator.EQUALS){
			generateEquals(builder, negated)
		}
		else if(operator == MultiValueConditionOperator.IN){
			generateIn(builder, negated)
		}
	}
	
	private def generateIn(UndecidedMatcherStatementBuilder builder, boolean negated){
		
	}
	
	private def generateEquals(UndecidedMatcherStatementBuilder builder, boolean negated){
		
	}
	
	
}