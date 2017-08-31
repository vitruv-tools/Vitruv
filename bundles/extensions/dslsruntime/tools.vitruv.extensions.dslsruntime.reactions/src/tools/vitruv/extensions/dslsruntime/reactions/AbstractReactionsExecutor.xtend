package tools.vitruv.extensions.dslsruntime.reactions

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.extensions.dslsruntime.reactions.helper.Change2ReactionsMap
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.util.command.ResourceAccess

abstract class AbstractReactionsExecutor extends AbstractEChangePropagationSpecification {
	private final static val LOGGER = Logger.getLogger(AbstractReactionsExecutor);

	private Change2ReactionsMap changeToReactionsMap;

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain);
		this.changeToReactionsMap = new Change2ReactionsMap();
		this.setup();
	}

	protected def void addReaction(Class<? extends EChange> eventType, IReactionRealization reaction) {
		this.changeToReactionsMap.addReaction(eventType, reaction);
	}

	private def Iterable<IReactionRealization> getRelevantReactions(EChange change) {
		return this.changeToReactionsMap.getReactions(change).filter[checkPrecondition(change)];
	}

	public override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return !change.relevantReactions.isEmpty
	}

	public override propagateChange(EChange event, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		if (event instanceof CompoundEChange) {
			for (atomicChange : event.atomicChanges) {
				propagateChange(atomicChange, correspondenceModel, resourceAccess);
			}
		}
		val relevantReactions = event.relevantReactions;
		LOGGER.trace("Call relevant reactions");
		for (reactions : relevantReactions) {
			LOGGER.debug("Calling reaction: " + reactions.class.simpleName + " with event: " + event);
			val executionState = new ReactionExecutionState(userInteracting, correspondenceModel, resourceAccess, this);
			reactions.applyEvent(event, executionState)
		}
	}

	override setUserInteracting(UserInteracting userInteracting) {
		super.setUserInteracting(userInteracting);
		changeToReactionsMap = new Change2ReactionsMap();
		setup();
	}

	protected abstract def void setup();

}
