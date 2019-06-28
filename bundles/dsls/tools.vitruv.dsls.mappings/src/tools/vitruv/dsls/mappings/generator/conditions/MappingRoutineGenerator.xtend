package tools.vitruv.dsls.mappings.generator.conditions

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import static tools.vitruv.dsls.mappings.generator.XExpressionParser.*
import tools.vitruv.dsls.mappings.generator.integration.AbstractReactionIntegrationGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.RemovedReactionGenerator

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
		ActionStatementBuilder builder) {
		if (reactionGenerator.applicableFor) {
			// integrate the routine in the file
			integrateRoutine(context)
			// call the routine from within this reactions action routine block
			builder.call(targetRoutineBuilder)
		}
	}

	private def integrateRoutine(ReactionGeneratorContext context) {
		if (targetRoutineBuilder === null) {
			targetRoutineBuilder = context.create.routine(AbstractReactionIntegrationGenerator.generateRoutine(routine),
				targetMetaclass)
			context.getSegmentBuilder += targetRoutineBuilder
		}
	}
}
