package tools.vitruv.dsls.mappings.generator.integration

import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionOrientation
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory

abstract class AbstractReactionIntegrationGenerator implements IReactionIntegrationGenerator{
	
	protected IReactionIntegrateable l2rIntegration
	protected IReactionIntegrateable r2lIntegration
	
	override check(Mapping mapping) {
		mapping.reactions.forEach [
			val reaction = generateReaction(it)
			if (orientation == ReactionOrientation.LEFT) {
				l2rIntegration.integrate(reaction)
			} else {
				r2lIntegration.integrate(reaction)
			}
		]
	 	mapping.routines.forEach [
			val routine = generateRoutine(it)
			l2rIntegration.integrate(routine)
			val routineCopy = generateRoutine(it)
			r2lIntegration.integrate(routineCopy)
		]
	}
	
	protected def generateReaction(ReactionIntegration reactionIntegration) {
		val reaction = ReactionsLanguageFactory.eINSTANCE.createReaction
		reaction.callRoutine = reactionIntegration.callRoutine
		reaction.documentation = reactionIntegration.documentation
		reaction.name = reactionIntegration.name
		reaction.trigger = reactionIntegration.trigger
		reaction
	}

	protected def generateRoutine(RoutineIntegration routineIntegration) {
		val routine = ReactionsLanguageFactory.eINSTANCE.createRoutine
		routine.name = routineIntegration.name
		routine.input = routineIntegration.input
		routine.documentation = routineIntegration.documentation
		routine.matcher = routineIntegration.matcher
		routine.action = routineIntegration.action
		routine
	}
}