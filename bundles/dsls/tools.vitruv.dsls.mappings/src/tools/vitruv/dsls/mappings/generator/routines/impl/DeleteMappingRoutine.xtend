package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class DeleteMappingRoutine extends tools.vitruv.dsls.mappings.generator.routines.AbstractMappingRoutineGenerator {

	new() {
		super('DeleteMapping')
	}

	override generateInput(InputBuilder builder) {
		builder.generateCorrespondingMappingParameterInput
	}

	override generate(MatcherOrActionBuilder builder) {
		builder.action [ actionBuilder |
			actionBuilder.generateAction
		]
	}

	private def generateAction(ActionStatementBuilder builder) {
		// 1) remove all correspondences (is it really needed? we dont know all the reaction objects)
		// builder.removeCorrespondences
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
