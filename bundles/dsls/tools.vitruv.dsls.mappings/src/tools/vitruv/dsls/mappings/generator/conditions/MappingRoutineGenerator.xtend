package tools.vitruv.dsls.mappings.generator.conditions

import java.util.List
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.integration.AbstractReactionIntegrationGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter

/**
 * 
 * a "fake" bidirectional condition generator
 * instead of generating routine logic this calls a user defined routine (in the mappings file) which emulates the bidirectional logic 
 * 
 */
class MappingRoutineGenerator extends AbstractBidirectionalCondition {

	private RoutineIntegration routine
	private FluentRoutineBuilder targetRoutineBuilder

	new(RoutineIntegration routineIntegration) {
		this.routine = routineIntegration
	}

	def isValidRoutine(List<MappingParameter> parameters) {
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
			val inputClass = input.get(i).metaclass.classifierID
			val parameterClass = parameters.get(i).value.metaclass.classifierID
			if (inputClass != parameterClass) {
				return false
			}
		}
		true
	}

	override generate(ReactionGeneratorContext context, AbstractReactionTypeGenerator reactionGenerator,
		ActionStatementBuilder builder) {
		// integrate the routine in the file
		integrateRoutine(context)
		// call the routine from within this reactions action routine block
		builder.call(
			targetRoutineBuilder,
			reactionGenerator.constructParameters
		)
	}

	private def constructParameters(AbstractReactionTypeGenerator reactionGenerator) {
		reactionGenerator.reactionParameters.map [
			new RoutineCallParameter(it.value.name)
		]
	}

	private def integrateRoutine(ReactionGeneratorContext context) {
		if (targetRoutineBuilder === null) {
			var generatedRoutine = AbstractReactionIntegrationGenerator.generateRoutine(routine)
			/* 	val ref = generatedRoutine.input.modelInputElements.get(0)
			 * 	val mm = MirBaseFactory.eINSTANCE.createMetamodelImport => [
			 * 		package = ref.metamodel.package
			 * 		name = ref.metamodel.name
			 * 	]

			 * 	generatedRoutine = ReactionsLanguageFactory.eINSTANCE.createRoutine => [
			 * 		name = 'updateRepoName'
			 * 		input = ReactionsLanguageFactory.eINSTANCE.createRoutineInput => [
			 * 			it.modelInputElements += MirBaseFactory.eINSTANCE.createNamedMetaclassReference => [
			 * 				name = 'affectedEObject'
			 * 				metaclass = ref.metaclass
			 * 				metamodel = mm
			 * 			]
			 * 		]
			 * 		action = ReactionsLanguageFactory.eINSTANCE.createAction => [
			 * 			actionStatements += ReactionsLanguageFactory.eINSTANCE.createExecuteActionStatement => [
			 * 				code = XbaseFactory.eINSTANCE.createXStringLiteral => [
			 * 					value = 'test'
			 * 				]
			 * 			]
			 * 		]
			 * 	]
			 */
			val inputBuilder = AbstractReactionIntegrationGenerator.generateRoutineInput(routine)
			targetRoutineBuilder = context.create.from(generatedRoutine, inputBuilder)
			context.getSegmentBuilder += targetRoutineBuilder
		}
	}

}
