package tools.vitruv.dsls.mappings.generator.integration

import java.util.function.Consumer
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionOrientation
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory

abstract class AbstractReactionIntegrationGenerator implements IReactionIntegrationGenerator {

	protected IReactionIntegrateable l2rIntegration
	protected IReactionIntegrateable r2lIntegration
	protected ReactionGeneratorContext l2rContext
	protected ReactionGeneratorContext r2lContext

	override init(ReactionGeneratorContext l2rContext, ReactionGeneratorContext r2lContext) {
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

	public static def generateRoutineInput(RoutineIntegration routineIntegration) {
		// it should have assignedEObject as first and string as second parameter
		if (routineIntegration.input !== null && routineIntegration.input.modelInputElements.size == 1 &&
			routineIntegration.input.javaInputElements.size == 1) {
			[ InputBuilder builder |
				val affecteEObject = routineIntegration.input.modelInputElements.get(0)
				builder.affectedEObject.apply(affecteEObject.metaclass)
				val modelParameter = routineIntegration.input.javaInputElements.get(0)
				val paramType = modelParameter.type.type.identifier
				val stringType = String.name
				if (paramType != stringType) {
					throw new IllegalStateException('''The integrated routine «routineIntegration.name» method signature is wrong. The second parameter must be a String!''')
				}
				builder.plain(String, modelParameter.name)
			] as Consumer<InputBuilder>

		} else {
			throw new IllegalStateException('''The integrated routine «routineIntegration.name» method signature is wrong. It should first have an affectedEObject and then a String parameter!''')
		}
	}
}