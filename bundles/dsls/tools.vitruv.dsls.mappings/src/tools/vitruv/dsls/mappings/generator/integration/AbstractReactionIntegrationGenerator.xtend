package tools.vitruv.dsls.mappings.generator.integration

import java.util.List
import java.util.function.Consumer
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionOrientation
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext

abstract class AbstractReactionIntegrationGenerator implements IReactionIntegrationGenerator {

	protected IReactionIntegrateable l2rIntegration
	protected IReactionIntegrateable r2lIntegration
	protected MappingGeneratorContext l2rContext
	protected MappingGeneratorContext r2lContext

	override init(MappingGeneratorContext l2rContext, MappingGeneratorContext r2lContext) {
		this.l2rContext = l2rContext
		this.r2lContext = r2lContext
		init()
	}

	protected abstract def void init()

	override check(Mapping mapping) {
		// integrate reactions to the specific side
		mapping.reactions.forEach [
			val reaction = generateReaction(it)
			if (orientation == ReactionOrientation.LEFT) {
				l2rIntegration.integrate(reaction)
			} else {
				r2lIntegration.integrate(reaction)
			}
		]
		// integrate routines 
		mapping.routines.forEach [
			val routine = generateRoutine(definition)
			if (orientation == ReactionOrientation.LEFT) {
				l2rIntegration.integrate(routine)
			} else {
				r2lIntegration.integrate(routine)
			}
		]
	}

	public static def generateReaction(ReactionIntegration reactionIntegration) {
		val reaction = ReactionsLanguageFactory.eINSTANCE.createReaction
		reaction.callRoutine = EcoreUtil.copy(reactionIntegration.callRoutine)
		reaction.documentation = reactionIntegration.documentation
		reaction.name = reactionIntegration.name
		reaction.trigger = EcoreUtil.copy(reactionIntegration.trigger)
		reaction
	}

	public static def generateRoutine(RoutineIntegration routineIntegration) {
		val routine = ReactionsLanguageFactory.eINSTANCE.createRoutine
		routine.name = routineIntegration.name
		routine.input = EcoreUtil.copy(routineIntegration.input)
//		routine.documentation = routineIntegration.documentation
		routine.matcher = EcoreUtil.copy(routineIntegration.matcher)
		routine.action = EcoreUtil.copy(routineIntegration.action)
		routine
	}

}
