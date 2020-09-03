package tools.vitruv.dsls.mappings.generator.conditions.impl

import java.util.List
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.integration.AbstractReactionIntegrationGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * 
 * a workaround bidirectional condition generator
 * instead of generating routine logic, this calls a user defined routine (in the mappings file) which emulates the bidirectional logic 
 * 
 */
class BidirectionalMappingRoutineGenerator implements AbstractBidirectionalCondition {

	@Accessors(PUBLIC_GETTER)
	var RoutineIntegration routine
	@Accessors(PUBLIC_GETTER)
	var FluentRoutineBuilder targetRoutineBuilder
	var List<MappingParameter> parameters

	new(List<MappingParameter> parameters, RoutineIntegration routineIntegration) {
		this.parameters = parameters
		this.routine = routineIntegration
	}
	
	/**
	 * Checks if the routine can be used to implement a bidirectional expression in this mapping
	 */
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

	def integrateRoutine(MappingGeneratorContext context) {
		var generatedRoutine = AbstractReactionIntegrationGenerator.generateRoutine(routine)
		targetRoutineBuilder = context.create.from(generatedRoutine)
		context.getSegmentBuilder += targetRoutineBuilder
	}

}
