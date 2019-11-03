package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.mappings.generator.routines.AbstractMappingRoutineGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.ExistingMappingCorrespondence
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder

class DeleteMappingRoutineGenerator extends AbstractMappingRoutineGenerator {

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
		// 1) remove all correspondences 
		// builder.removeCorrespondences
		// 2) delete all corresponding elements
		builder.deleteCorrespondingElements
	}

	//TODO: find a way to delete all correspondences of a mapping without knowing all the reaction objects (could have been deleted)
	private def removeCorrespondences(ActionStatementBuilder builder) {
		iterateParameters([ reactionParameter, correspondingParameter |
			val element = correspondingParameter.parameterName
			builder.removeCorrespondenceBetween(element).and(reactionParameter.parameterName).taggedWith(
				reactionParameter, correspondingParameter)
		])
	}

	private def deleteCorrespondingElements(ActionStatementBuilder builder) {
		//only delete actual mapping parameters
		correspondingParameters.filter[!(it instanceof ExistingMappingCorrespondence)].forEach [ correspondingParameter |
			val element = correspondingParameter.parameterName
			builder.delete(element)
		]
	}
}
