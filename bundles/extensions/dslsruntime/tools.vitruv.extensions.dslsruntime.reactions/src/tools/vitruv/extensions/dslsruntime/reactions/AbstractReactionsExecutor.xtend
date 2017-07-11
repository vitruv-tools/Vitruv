package tools.vitruv.extensions.dslsruntime.reactions

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.extensions.dslsruntime.reactions.helper.Change2ReactionsMap
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.ChangePropagationResult

abstract class AbstractReactionsExecutor extends AbstractEChangePropagationSpecification {
	static extension Logger = Logger::getLogger(AbstractReactionsExecutor)
	Change2ReactionsMap changeToReactionsMap

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain)
		changeToReactionsMap = new Change2ReactionsMap
		setup
	}

	protected def void addReaction(Class<? extends EChange> eventType, IReactionRealization reaction) {
		changeToReactionsMap.addReaction(eventType, reaction)
	}

	private def Iterable<IReactionRealization> getRelevantReactions(EChange change) {
		changeToReactionsMap.getReactions(change).filter[checkPrecondition(change)]
	}

	override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		!change.relevantReactions.empty
	}

	override propagateChange(EChange event, CorrespondenceModel correspondenceModel) {
		val propagationResult = new ChangePropagationResult
		if (event instanceof CompoundEChange) {
			for (atomicChange : event.atomicChanges) {
				propagationResult.integrateResult(propagateChange(atomicChange, correspondenceModel))
			}
		}
		val relevantReactionss = event.relevantReactions
		debug("Call relevant reactions")
		for (reactions : relevantReactionss) {
			debug(reactions.toString)
			val executionState = new ReactionExecutionState(userInteracting, correspondenceModel,
				new ChangePropagationResult, this)
			val currentPropagationResult = reactions.applyEvent(event, executionState)
			propagationResult.integrateResult(currentPropagationResult)
		}
		return propagationResult
	}

	override setUserInteracting(UserInteracting userInteracting) {
		super.userInteracting = userInteracting
		changeToReactionsMap = new Change2ReactionsMap
		setup
	}

	protected abstract def void setup()
}
