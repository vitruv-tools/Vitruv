package tools.vitruv.dsls.mappings.generator.routines

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class BidirectionalUpdateRoutineGenerator extends AbstractMappingRoutineGenerator {

	new() {
		super('BidirectionalUpdate')
	}

	override generateInput() {
		generateMappingParameterInput
	}

	override generate(MatcherOrActionBuilder builder) {
		builder.action [ actionBuilder |
			if (bidirectionConditions.empty) {
				// just create an return
				actionBuilder.noAction
			}
			bidirectionConditions.forEach [
				it.generate(actionBuilder)
			]
		]
	}

}
