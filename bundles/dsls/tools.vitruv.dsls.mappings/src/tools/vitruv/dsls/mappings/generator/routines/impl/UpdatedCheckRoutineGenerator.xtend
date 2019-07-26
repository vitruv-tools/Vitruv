package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

class UpdatedCheckRoutineGenerator extends tools.vitruv.dsls.mappings.generator.routines.AbstractRetrievalCheckRoutineGenerator {

	private DeletedCheckRoutineGenerator deletedCheckRoutineGenerator

	new() {
		super('ElementUpdatedCheck')
		deletedCheckRoutineGenerator = new DeletedCheckRoutineGenerator
	}

	override onSuccessfullyRetrievingParameters(RoutineTypeProvider provider) {
		[
			provider.callViaVariables(CreateMappingRoutine.routine, reactionParameters)
		]
	}

	override onFailedToRetrieveParameters(RoutineTypeProvider provider) {
		[
			provider.call(DeletedCheckRoutineGenerator.routine,
				#[provider.variable(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)])
		]
	}
}
