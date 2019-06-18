package tools.vitruv.dsls.mappings.generator.action

import java.util.List
import java.util.function.Consumer
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.trigger.AbstractReactionTypeGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder

import static tools.vitruv.dsls.mappings.generator.XExpressionParser.*

class ReactionActionGenerator implements Consumer<ActionStatementBuilder>{
	
	new(AbstractReactionTypeGenerator generator, List<AbstractBidirectionalCondition> conditions) {
	}
	
	override accept(ActionStatementBuilder builder) {
		builder.execute([
			parseExpression('''50''')
		])
	}
	
}