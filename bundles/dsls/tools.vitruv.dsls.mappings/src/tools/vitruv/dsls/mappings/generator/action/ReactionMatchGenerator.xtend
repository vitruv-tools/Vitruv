package tools.vitruv.dsls.mappings.generator.action

import java.util.function.Consumer
import tools.vitruv.dsls.mappings.generator.conditions.SingleSidedConditionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class ReactionMatchGenerator implements Consumer<UndecidedMatcherStatementBuilder>{
	
	private AbstractReactionTypeGenerator generator
	private SingleSidedConditionGenerator conitionsGenerator
	
	new(AbstractReactionTypeGenerator generator,SingleSidedConditionGenerator conitionsGenerator) {
		this.generator = generator
		this.conitionsGenerator = conitionsGenerator
	}
	
	override accept(UndecidedMatcherStatementBuilder builder) {
		//do correspondence matches
		generator.generateCorrespondenceMatches(builder)
		//check additional restrictions from single sided conditions
	//	conitionsGenerator.accept(builder)
	}
	
}