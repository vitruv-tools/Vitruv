package tools.vitruv.dsls.mappings.generator.routines

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class DeleteMappingRoutine extends AbstractMappingRoutineGenerator {

	new() {
		super('DeleteMapping')
	}

	override generateInput() {
		generateMappingParameterInput
	}

	override generate(MatcherOrActionBuilder builder) {
		builder.match [ matchBuilder |
			matchBuilder.generateMatch
		]
		builder.action [ actionBuilder |
			actionBuilder.generateAction
		]
	}

	private def generateMatch(UndecidedMatcherStatementBuilder builder) {
		// just take the first element to retrieve all the corresponding elements (any other would work as well)
		val taggingParameter = reactionParameters.get(0)
		correspondingParameters.forEach [ correspondingParameter |
			val element = correspondingParameter.parameterName
			builder.vall(element).retrieve(correspondingParameter.value.metaclass).correspondingTo(
				taggingParameter.parameterName).taggedWith(taggingParameter, correspondingParameter)
		]
	}

	private def generateAction(ActionStatementBuilder builder) {
		// 1) remove all correspondences
		builder.removeCorrespondences
		// 2) delete all corresponding elements
		builder.deleteCorrespondingElements
	}

	private def removeCorrespondences(ActionStatementBuilder builder) {
		iterateParameters([ reactionParameter, correspondingParameter |
			val element = correspondingParameter.parameterName
			builder.removeCorrespondenceBetween(element).and(reactionParameter.parameterName).taggedWith(
				reactionParameter, correspondingParameter)
		])
	}

	private def deleteCorrespondingElements(ActionStatementBuilder builder) {
		correspondingParameters.forEach [ correspondingParameter |
			val element = correspondingParameter.parameterName
			builder.delete(element)
		]
	}
}
