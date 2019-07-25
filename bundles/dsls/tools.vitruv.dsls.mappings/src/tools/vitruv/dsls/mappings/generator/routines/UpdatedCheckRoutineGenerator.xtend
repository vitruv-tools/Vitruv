package tools.vitruv.dsls.mappings.generator.routines

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class UpdatedCheckRoutineGenerator extends AbstractMappingRoutineGenerator {

	new() {
		super('ElementUpdatedCheck')
	}

	override generateInput() {
		generateSingleEObjectInput
	}

	override generate(MatcherOrActionBuilder builder) {
		builder.debugRoutine
	}

}
