package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

class CreatedCheckRoutineGenerator extends tools.vitruv.dsls.mappings.generator.routines.AbstractRetrievalCheckRoutineGenerator {

	new() {
		super('ElementCreatedCheck')
	}


	override onSuccessfullyRetrievingParameters(RoutineTypeProvider provider) {
		[
			provider.callViaVariables(CreateMappingRoutineGenerator.routine, reactionParameters)
		]
	}

	override onFailedToRetrieveParameters(RoutineTypeProvider provider) {
		// nothing to do
		null
	}

}
