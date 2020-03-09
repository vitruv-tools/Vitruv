package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.mappings.generator.routines.AbstractRetrievalCheckRoutineGenerator
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

class CreateOrDeleteCheckRoutineGenerator extends AbstractRetrievalCheckRoutineGenerator {

	new() {
		super('ElementUpdatedCheck')
	}

	override onSuccessfullyRetrievingParameters(TypeProvider provider) {
		[
			provider.callViaVariables(CreateMappingRoutineGenerator.routine, reactionParameters)
		]
	}

	override onFailedToRetrieveParameters(TypeProvider provider) {
		[
			provider.call(DeletedCheckRoutineGenerator.routine,
				#[provider.variable(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)])
		]
	}
}
