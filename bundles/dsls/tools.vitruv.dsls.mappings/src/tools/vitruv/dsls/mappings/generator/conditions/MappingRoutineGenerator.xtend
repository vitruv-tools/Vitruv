package tools.vitruv.dsls.mappings.generator.conditions

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.integration.AbstractReactionIntegrationGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.RemovedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter

/**
 * 
 * a "fake" bidirectional condition generator
 * instead of generating routine logic this calls a user defined routine (in the mappings file) which emulates the bidirectional logic 
 * 
 */
class MappingRoutineGenerator extends AbstractBidirectionalCondition {

	private RoutineIntegration routine
	private EClass targetMetaclass
	private FluentRoutineBuilder targetRoutineBuilder

	new(RoutineIntegration routineIntegration) {
		this.targetMetaclass = findTargetMetaclass(routineIntegration)
		this.routine = routineIntegration
	}

	private def findTargetMetaclass(RoutineIntegration routineIntegration) {
		val input = routineIntegration.input
		val modelElements = input.modelInputElements
		val model = modelElements.get(0)
		model.metaclass
	}

	private def isApplicableFor(AbstractReactionTypeGenerator generator) {
		// check if this routine should be called by the reaction
		if (generator.metaclass == targetMetaclass) {
			// dont call from delete reactions
			if (generator instanceof DeletedReactionGenerator || generator instanceof RemovedReactionGenerator) {
				return false
			}
			return true
		}
		return false
	}

	override generate(ReactionGeneratorContext context, AbstractReactionTypeGenerator reactionGenerator,
		ActionStatementBuilder builder, MappingParameter parameter) {
		if (reactionGenerator.applicableFor) {
			// integrate the routine in the file
			integrateRoutine(context)
			// call the routine from within this reactions action routine block
			builder.call(
				targetRoutineBuilder,
				new RoutineCallParameter(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE),
				new RoutineCallParameter(XbaseFactory.eINSTANCE.createXStringLiteral => [value = parameter.value.name])
			)
		}
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
			targetRoutineBuilder = context.create.from(generatedRoutine)

			/*val parameter = generatedRoutine.input.modelInputElements.get(0)
			 * 		 	parameter.e
			 * var modelRou = generatedRoutine.input.modelInputElements.get(0);
			 * var modelNew = MirBaseFactory.eINSTANCE.createNamedMetaclassReference => [
			 * 	it.name = ' test'
			 ]*/
			context.getSegmentBuilder += targetRoutineBuilder
		}
	}
}
