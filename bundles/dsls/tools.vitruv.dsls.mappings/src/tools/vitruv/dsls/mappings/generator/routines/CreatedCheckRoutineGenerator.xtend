package tools.vitruv.dsls.mappings.generator.routines

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class CreatedCheckRoutineGenerator extends AbstractMappingRoutineGenerator {

	new() {
		super('ElementCreatedCheck')
	}

	override generateInput() {
		[ builder |
			builder.generateSingleEObjectInput
		]

	}

	/*
	 * 1) check with 
	 * 
	 * 2)
	 * 
	 */
	override generate(MatcherOrActionBuilder builder) {
		builder.debugRoutine
	}

}
