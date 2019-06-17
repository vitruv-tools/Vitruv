package tools.vitruv.dsls.mappings.generator.action

import java.util.function.Consumer
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.mappings.generator.trigger.ReactionTypeGenerator
import java.util.List
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition

class ReactionActionGenerator implements Consumer<ActionStatementBuilder>{
	
	new(ReactionTypeGenerator generator, List<AbstractBidirectionalCondition> conditions) {
	}
	
	override accept(ActionStatementBuilder builder) {
	}
	
}