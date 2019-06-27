package tools.vitruv.dsls.mappings.generator.action

import java.util.List
import java.util.function.Consumer
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder

class ReactionActionGenerator implements Consumer<ActionStatementBuilder>{
	
	private AbstractReactionTypeGenerator generator
	private List<AbstractBidirectionalCondition> conditions
	private ReactionGeneratorContext context
	
	new(AbstractReactionTypeGenerator generator, List<AbstractBidirectionalCondition> conditions, ReactionGeneratorContext context) {
		this.generator = generator
		this.conditions = conditions
		this.context =context
	}
	
	override accept(ActionStatementBuilder builder) {
		//do correspondence actions
		generator.generateCorrespondenceActions(builder)
		//enforce bidirectional consistency rules
		conditions.forEach[generate(context, generator, builder)]
	}
	
}