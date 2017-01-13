package tools.vitruv.extensions.dslsruntime.reactions

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.extensions.dslsruntime.reactions.helper.Change2ReactionsMap
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.change.echange.compound.CompoundEChange

abstract class AbstractReactionsExecutor extends AbstractEChangePropagationSpecification {
	private final static val LOGGER = Logger.getLogger(AbstractReactionsExecutor);

	private Change2ReactionsMap changeToReactionsMap;
	private final MetamodelPair metamodelPair;
	
	new (UserInteracting userInteracting, MetamodelPair metamodelPair) {
		super(userInteracting);
		this.changeToReactionsMap = new Change2ReactionsMap();
		this.metamodelPair = metamodelPair;
		this.setup();
	}
	
	override getMetamodelPair() {
		return metamodelPair;
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
	
	public override propagateChange(EChange event, CorrespondenceModel correspondenceModel) {
		val propagationResult = new ChangePropagationResult();
		if (event instanceof CompoundEChange) {
			for (atomicChange : event.atomicChanges) {
				propagationResult.integrateResult(propagateChange(atomicChange, correspondenceModel));
			}
		}
		val relevantReactionss = event.relevantReactions;
		LOGGER.debug("Call relevant reactions");
		for (reactions : relevantReactionss) {
			LOGGER.debug(reactions.toString());
			val currentPropagationResult = reactions.applyEvent(event, correspondenceModel)
			propagationResult.integrateResult(currentPropagationResult);
		}
		return propagationResult;
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		super.setUserInteracting(userInteracting);
		changeToReactionsMap = new Change2ReactionsMap();
		setup();
	}
	
	protected abstract def void setup();
	
}