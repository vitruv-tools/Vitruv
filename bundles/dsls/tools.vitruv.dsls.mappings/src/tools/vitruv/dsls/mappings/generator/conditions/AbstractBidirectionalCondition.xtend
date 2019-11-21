package tools.vitruv.dsls.mappings.generator.conditions

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder

interface AbstractBidirectionalCondition {

	def void generate(ActionStatementBuilder builder)
}
