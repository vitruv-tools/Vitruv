package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.mappings.generator.routines.AbstractRetrievalCheckRoutineGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

class BidirectionalCheckRoutineGenerator extends AbstractRetrievalCheckRoutineGenerator {

	new() {
		super('BidirectionalCheck')
	}

	override onSuccessfullyRetrievingParameters(RoutineTypeProvider provider) {
		[
			provider.callViaVariables(BidirectionalUpdateRoutineGenerator.routine, reactionParameters)
		]
	}

	override onFailedToRetrieveParameters(RoutineTypeProvider provider) {
		// nothing to do
		null
	}

}
