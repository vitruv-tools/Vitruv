package tools.vitruv.dsls.mappings.generator.routines.impl

import tools.vitruv.dsls.mappings.generator.routines.AbstractMappingRoutineGenerator
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
		// delete all corresponding elements (also removes correspondences)
		builder.deleteCorrespondingElements
	}
	
	private def deleteCorrespondingElements(ActionStatementBuilder builder) {
		//only delete actual mapping parameters
		correspondingParameters.filterNonExistingMappingParameters.forEach [ correspondingParameter |
			val element = correspondingParameter.parameterName
			builder.delete(element)
		]
	}
}
