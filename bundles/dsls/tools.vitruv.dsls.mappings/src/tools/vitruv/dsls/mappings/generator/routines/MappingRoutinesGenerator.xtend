package tools.vitruv.dsls.mappings.generator.routines

import java.util.List
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.BidirectionalCheckRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.BidirectionalUpdateRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.CreateMappingRoutine
import tools.vitruv.dsls.mappings.generator.routines.impl.CreatedCheckRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.DeleteMappingRoutine
import tools.vitruv.dsls.mappings.generator.routines.impl.DeletedCheckRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.UpdatedCheckRoutineGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

class MappingRoutinesGenerator {

	@Extension
	private MappingRoutineStorage routineStorage

	new(List<MappingParameter> fromParameters, List<MappingParameter> toParameters) {
		routineStorage = new MappingRoutineStorage(fromParameters, toParameters)
	}

	public def generateRoutines(String mappingName, ReactionGeneratorContext context,
		List<AbstractSingleSidedCondition> singleSidedConditions,
		List<AbstractSingleSidedCondition> correspondingSingleSidedConditions,
		List<AbstractBidirectionalCondition> bidirectionalConditions) {
		routineStorage.init(mappingName, context, singleSidedConditions,correspondingSingleSidedConditions, bidirectionalConditions)
		//order is important here, because of routine calls (they have to be generated before they can be called)
		new BidirectionalUpdateRoutineGenerator().generateRoutine
		new CreateMappingRoutine().generateRoutine // calls BidirectionalUpdateRoutineGenerator
		new DeleteMappingRoutine().generateRoutine		
		new BidirectionalCheckRoutineGenerator().generateRoutine // calls BidirectionalUpdateRoutineGenerator
		new CreatedCheckRoutineGenerator().generateRoutine // calls CreateMappingRoutine
		new DeletedCheckRoutineGenerator().generateRoutine // calls DeleteMappingRoutine
		new UpdatedCheckRoutineGenerator().generateRoutine // calls CreateMappingRoutine or DeletedCheckRoutineGenerator
	}

	public def generateRoutineCall(AbstractReactionTriggerGenerator generator) {
		if (generator.derivedFromBidirectionalCondition) {
			return BidirectionalCheckRoutineGenerator.routineBuilder
		}
		switch (generator.triggerType) {
			case CREATE: return CreatedCheckRoutineGenerator.routineBuilder
			case UPDATE: return UpdatedCheckRoutineGenerator.routineBuilder
			case DELETE: return DeletedCheckRoutineGenerator.routineBuilder
		}
	}

	private def generateCall(ActionStatementBuilder builder, FluentRoutineBuilder target) {
		builder.call(target, new RoutineCallParameter(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE))
		null
	}

}
