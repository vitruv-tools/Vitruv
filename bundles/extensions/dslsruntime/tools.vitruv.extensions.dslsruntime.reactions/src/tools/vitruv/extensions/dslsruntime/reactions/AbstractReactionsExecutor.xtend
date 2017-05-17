package tools.vitruv.extensions.dslsruntime.reactions

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.extensions.dslsruntime.reactions.helper.Change2ReactionsMap
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.domains.VitruvDomain

abstract class AbstractReactionsExecutor extends AbstractEChangePropagationSpecification {
	static val LOGGER = Logger::getLogger(AbstractReactionsExecutor)

	Change2ReactionsMap changeToReactionsMap

	new(UserInteracting userInteracting, VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(userInteracting, sourceDomain, targetDomain)
		changeToReactionsMap = new Change2ReactionsMap
		setup
	}

	protected def void addReaction(Class<? extends EChange> eventType, IReactionRealization reaction) {
		changeToReactionsMap.addReaction(eventType, reaction)
	}

	private def Iterable<IReactionRealization> getRelevantReactions(EChange change) {
		changeToReactionsMap.getReactions(change).filter[checkPrecondition(change)]
	}

	public override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		!change.relevantReactions.empty
	}

	public override propagateChange(EChange event, CorrespondenceModel correspondenceModel) {
		val propagationResult = new ChangePropagationResult
		if (event instanceof CompoundEChange) {
			event.atomicChanges.forEach[propagationResult.integrateResult(propagateChange(it, correspondenceModel))]
		}
		val relevantReactionss = event.relevantReactions
		LOGGER.debug("Call relevant reactions")
		for (reactions : relevantReactionss) {
			LOGGER.debug(reactions.toString)
			val currentPropagationResult = reactions.applyEvent(event, correspondenceModel)
			propagationResult.integrateResult(currentPropagationResult)
		}
		propagationResult
	}

	override setUserInteracting(UserInteracting userInteracting) {
		super.userInteracting = userInteracting
		changeToReactionsMap = new Change2ReactionsMap
		setup
	}

	protected abstract def void setup()

}
