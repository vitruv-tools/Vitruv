package tools.vitruv.dsls.mappings.generator.routines

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class DeletedCheckRoutineGenerator extends AbstractMappingRoutineGenerator {

	new() {
		super('ElementDeletedCheck')
	}

	override generateInput() {
		generateSingleEObjectInput
	}

	override generate(MatcherOrActionBuilder builder) {
		builder.debugRoutine
	}

}
