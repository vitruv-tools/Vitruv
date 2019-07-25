package tools.vitruv.dsls.mappings.generator.routines

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class BidirectionalCheckRoutineGenerator extends AbstractMappingRoutineGenerator{
	
	new() {
		super('BidirectionalCheck')
	}
	
	override generateInput() {
		generateSingleEObjectInput
	}
	
	override generate(MatcherOrActionBuilder builder) {
		builder.debugRoutine
	}
		
}