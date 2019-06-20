package tools.vitruv.dsls.mappings.generator.action

import java.util.List
import java.util.function.Consumer
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder

class ReactionActionGenerator implements Consumer<ActionStatementBuilder>{
	
	private AbstractReactionTypeGenerator generator
	private List<AbstractBidirectionalCondition> conditions
	
	new(AbstractReactionTypeGenerator generator, List<AbstractBidirectionalCondition> conditions) {
		this.generator = generator
		this.conditions = conditions
	}
	
	override accept(ActionStatementBuilder builder) {
		//do correspondence actions
		generator.generateCorrespondenceActions(builder)
		//enforce bidirectional consistency rules
		
	 /* 	builder.execute([
			parseExpression('''''')
		])*/
	}
	
}