package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.mappings.generator.routines.AbstractRetrievalCheckRoutineGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

class CreateOrDeleteCheckRoutineGenerator extends AbstractRetrievalCheckRoutineGenerator {

	new() {
		super('ElementUpdatedCheck')
	}

	override onSuccessfullyRetrievingParameters(RoutineTypeProvider provider) {
		[
			provider.callViaVariables(CreateMappingRoutineGenerator.routine, reactionParameters)
		]
	}

	override onFailedToRetrieveParameters(RoutineTypeProvider provider) {
		[
			provider.call(DeletedCheckRoutineGenerator.routine,
				#[provider.variable(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)])
		]
	}
}
