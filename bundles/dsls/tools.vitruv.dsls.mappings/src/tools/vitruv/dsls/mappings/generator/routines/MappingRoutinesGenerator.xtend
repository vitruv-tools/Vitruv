package tools.vitruv.dsls.mappings.generator.routines

import java.util.List
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.CreateOrDeleteCheckRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.CreatedCheckRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.DeletedCheckRoutineGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants
import tools.vitruv.dsls.mappings.generator.routines.impl.UpdateMappingRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.DeleteMappingRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.UpdateCheckRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.impl.CreateMappingRoutineGenerator

class MappingRoutinesGenerator {

	@Extension
	var MappingRoutineStorage routineStorage

	new(List<MappingParameter> fromParameters, List<MappingParameter> toParameters) {
		routineStorage = new MappingRoutineStorage(fromParameters, toParameters)
	}
	
	/**
	 * Generates the routines for a mapping specification
	 */
	def generateRoutines(String mappingName, MappingGeneratorContext context,
		List<AbstractSingleSidedCondition<?>> singleSidedConditions,
		List<AbstractSingleSidedCondition<?>> correspondingSingleSidedConditions,
		List<AbstractBidirectionalCondition> bidirectionalConditions) {
		routineStorage.init(mappingName, context, singleSidedConditions, correspondingSingleSidedConditions,
			bidirectionalConditions)
		// order is important here, because of routine calls (they have to be generated before they can be called)
		new UpdateMappingRoutineGenerator().generateRoutine
		new CreateMappingRoutineGenerator().generateRoutine // calls UpdateMappingRoutine
		new DeleteMappingRoutineGenerator().generateRoutine
		new UpdateCheckRoutineGenerator().generateRoutine // calls UpdateMappingRoutine
		new CreatedCheckRoutineGenerator().generateRoutine // calls CreateMappingRoutine
		new DeletedCheckRoutineGenerator().generateRoutine // calls DeleteMappingRoutine
		new CreateOrDeleteCheckRoutineGenerator().generateRoutine // calls CreateMappingRoutine or DeletedCheckRoutineGenerator 
	}

	/**
	 * Connects a reaction trigger with a generated routine by creating a routine-call
	 */
	def generateRoutineCall(PreconditionOrRoutineCallBuilder reactionBuilder,
		AbstractReactionTriggerGenerator generator) {
		switch (generator.getScenarioType) {
			case CREATE:
				return reactionBuilder.callRoutine(CreatedCheckRoutineGenerator)
			case CREATE_OR_DELETE:
				return reactionBuilder.callRoutine(CreateOrDeleteCheckRoutineGenerator)
			case DELETE:
				return reactionBuilder.callRoutine(DeletedCheckRoutineGenerator)
			case UPDATE: {
				val observeChange = generator.sourceObserveChange
				val routineName = observeChange.routine.name
				return reactionBuilder.callBidirectionalRoutine(routineName)
			}
		}
	}

	private def callBidirectionalRoutine(PreconditionOrRoutineCallBuilder reactionBuilder, String routineName) {
		reactionBuilder.call [
			alwaysRequireAffectedEObject
			action[
				val affectedEObject = new RoutineCallParameter(
					ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
				val routineNameParameter = new RoutineCallParameter(XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = routineName
				])
				call(UpdateCheckRoutineGenerator.routineBuilder, affectedEObject, routineNameParameter)
			]
		]
	}

	private def callRoutine(PreconditionOrRoutineCallBuilder reactionBuilder,
		Class<? extends AbstractMappingRoutineGenerator> routine) {
		reactionBuilder.call(routine.routineBuilder)
	}

}
