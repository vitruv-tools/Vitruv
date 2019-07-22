package tools.vitruv.dsls.mappings.generator.conditions

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter

abstract class AbstractBidirectionalCondition{
	
	public abstract def void generate(ReactionGeneratorContext context, AbstractReactionTypeGenerator reactionGenerator, ActionStatementBuilder builder, MappingParameter parameter);
}