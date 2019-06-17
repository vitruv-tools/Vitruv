package tools.vitruv.dsls.mappings.generator.conditions

import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.function.Consumer
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.mappings.mappingsLanguage.EnforceableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.CheckAndEnforceCondition

class SingleSidedConditionGenerator implements Consumer<UndecidedMatcherStatementBuilder> {
	
	private List<SingleSidedCondition> conditions;
	
	new(List<SingleSidedCondition> conditions){
		this.conditions=conditions;
	}
	
	override accept(UndecidedMatcherStatementBuilder builder) {
		conditions.forEach[condition |
			val generator = SingleSidedConditionFactory.construct(condition)
					
		]
	}
	
}