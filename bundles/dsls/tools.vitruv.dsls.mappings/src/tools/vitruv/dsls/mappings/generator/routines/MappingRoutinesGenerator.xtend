package tools.vitruv.dsls.mappings.generator.routines

import java.util.HashMap
import java.util.List
import java.util.Map
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition

class MappingRoutinesGenerator {

	@Extension
	private MappingRoutineStorage routineStorage

	new(List<MappingParameter> fromParameters, List<MappingParameter> toParameters) {
		routineStorage = new MappingRoutineStorage(fromParameters, toParameters)
	}

	public def generateRoutines(String mappingName, ReactionGeneratorContext context,
		List<AbstractSingleSidedCondition> singleSidedConditions,
		List<AbstractBidirectionalCondition> bidirectionalConditions) {
		routineStorage.init(mappingName, context, singleSidedConditions, bidirectionalConditions)
		new CreatedCheckRoutineGenerator().generateRoutine
		new DeletedCheckRoutineGenerator().generateRoutine
		new UpdatedCheckRoutineGenerator().generateRoutine
		new BidirectionalUpdateRoutineGenerator().generateRoutine
	//	new CreateMappingRoutine().generateRoutine
	//	new DeleteMappingRoutine().generateRoutine
	}

	public def generateRoutineCall(AbstractReactionTriggerGenerator generator) {
		if (generator.derivedFromBidirectionalCondition) {
			return BidirectionalUpdateRoutineGenerator.routine
		}
		switch (generator.triggerType) {
			case CREATE: return CreatedCheckRoutineGenerator.routine
			case UPDATE: return UpdatedCheckRoutineGenerator.routine
			case DELETE: return DeletedCheckRoutineGenerator.routine
		}
	}

	private def generateCall(ActionStatementBuilder builder, FluentRoutineBuilder target) {
		builder.call(target, new RoutineCallParameter(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE))
		null
	}

}
