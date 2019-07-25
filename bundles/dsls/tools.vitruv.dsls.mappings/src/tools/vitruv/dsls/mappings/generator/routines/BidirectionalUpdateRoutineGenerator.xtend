package tools.vitruv.dsls.mappings.generator.routines

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class BidirectionalUpdateRoutineGenerator extends AbstractMappingRoutineGenerator{
	
	new() {
		super('BidirectionalUpdate')
	}
	
	override generateInput() {
		generateSingleEObjectInput
	}
	
	//todo: just call all bidirectional conditions routines
	override generate(MatcherOrActionBuilder builder) {
		builder.debugRoutine
	}
		
}