package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.mappings.generator.routines.AbstractRetrievalCheckRoutineGenerator
import tools.vitruv.dsls.reactions.builder.TypeProvider

class CreatedCheckRoutineGenerator extends AbstractRetrievalCheckRoutineGenerator {

	new() {
		super('ElementCreatedCheck')
	}


	override onSuccessfullyRetrievingParameters(TypeProvider provider) {
		[
			provider.callViaVariables(CreateMappingRoutineGenerator.routine, reactionParameters)
		]
	}

	override onFailedToRetrieveParameters(TypeProvider provider) {
		// nothing to do
		null
	}

}
