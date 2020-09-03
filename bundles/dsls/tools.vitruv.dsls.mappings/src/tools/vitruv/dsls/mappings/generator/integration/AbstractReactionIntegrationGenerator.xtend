package tools.vitruv.dsls.mappings.generator.integration

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionOrientation
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory

abstract class AbstractReactionIntegrationGenerator implements IReactionIntegrationGenerator {

	var IReactionIntegrateable l2rIntegration
	var IReactionIntegrateable r2lIntegration
	@Accessors(PROTECTED_GETTER)
	var MappingGeneratorContext l2rContext
	@Accessors(PROTECTED_GETTER)
	var MappingGeneratorContext r2lContext

	override init(MappingGeneratorContext l2rContext, MappingGeneratorContext r2lContext) {
		this.l2rContext = l2rContext
		this.r2lContext = r2lContext
		init()
	}

	protected abstract def void init()
	
	protected def void initIntegration(IReactionIntegrateable l2rIntegration, IReactionIntegrateable r2lIntegration){
		this.l2rIntegration = l2rIntegration
		this.r2lIntegration = r2lIntegration
	}
	
	/**
	 * Inspects the mapping specification for integrated reaction elements
	 */
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
	
	/**
	 * Creates a reaction from an integrated reaction specification
	 */
	static def generateReaction(ReactionIntegration reactionIntegration) {
		val reaction = ReactionsLanguageFactory.eINSTANCE.createReaction
		reaction.callRoutine = EcoreUtil.copy(reactionIntegration.callRoutine)
		reaction.documentation = reactionIntegration.documentation
		reaction.name = reactionIntegration.name
		reaction.trigger = EcoreUtil.copy(reactionIntegration.trigger)
		reaction
	}

	/**
	 * Creates a routine from an integrated routine specification
	 */
	static def generateRoutine(RoutineIntegration routineIntegration) {
		val routine = ReactionsLanguageFactory.eINSTANCE.createRoutine
		routine.name = routineIntegration.name
		routine.input = EcoreUtil.copy(routineIntegration.input)
//		routine.documentation = routineIntegration.documentation
		routine.matcher = EcoreUtil.copy(routineIntegration.matcher)
		routine.action = EcoreUtil.copy(routineIntegration.action)
		routine
	}

}
