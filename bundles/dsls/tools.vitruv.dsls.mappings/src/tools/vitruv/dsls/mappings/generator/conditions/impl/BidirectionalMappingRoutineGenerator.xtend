package tools.vitruv.dsls.mappings.generator.conditions.impl

import java.util.List
import java.util.function.Consumer
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.integration.AbstractReactionIntegrationGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext

/**
 * 
 * a "fake" bidirectional condition generator
 * instead of generating routine logic this calls a user defined routine (in the mappings file) which emulates the bidirectional logic 
 * 
 */
class BidirectionalMappingRoutineGenerator implements AbstractBidirectionalCondition {

	private RoutineIntegration routine
	private FluentRoutineBuilder targetRoutineBuilder
	private List<MappingParameter> parameters

	new(List<MappingParameter> parameters, RoutineIntegration routineIntegration) {
		this.parameters = parameters
		this.routine = routineIntegration
	}

	def isValidRoutine() {
		// routine input must be exactly the mapping parameters 
		val input = routine.input.modelInputElements
		if (routine.input.javaInputElements.size > 0) {
			// not allowed to have other parameters
			return false
		}
		// check size
		if (input.size != parameters.size) {
			return false
		}
		// check metaclasses
		for (var i = 0; i < input.length; i++) {
			val inputClass = input.get(i).metaclass.instanceTypeName
			val parameterClass = parameters.get(i).value.metaclass.instanceTypeName
			if (inputClass != parameterClass) {
				return false
			}
		}
		true
	}

	override generate(ActionStatementBuilder builder) {
		builder.call(
			targetRoutineBuilder,
			constructParameters
		)
	}

	private def constructParameters() {
		parameters.map [
			new RoutineCallParameter(it.value.name+'_')
		]
	}

	public def integrateRoutine(MappingGeneratorContext context) {
		var generatedRoutine = AbstractReactionIntegrationGenerator.generateRoutine(routine)
		targetRoutineBuilder = context.create.from(generatedRoutine)
		context.getSegmentBuilder += targetRoutineBuilder
	}

//	private def generateRoutineInput() {
//		[ InputBuilder builder |
//			routine.input.modelInputElements.forEach [
//				builder.model(it.metaclass, it.name)
//			]
//		] as Consumer<InputBuilder>
//	}
}
