package tools.vitruv.extensions.dslsruntime.reactions

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.util.command.ResourceAccess
import java.util.List

abstract class AbstractReactionsExecutor extends AbstractEChangePropagationSpecification {
	private final static val LOGGER = Logger.getLogger(AbstractReactionsExecutor);

	private List<IReactionRealization> reactions;

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain);
		this.reactions = newArrayList;
		this.setup();
	}

	protected def void addReaction(IReactionRealization reaction) {
		this.reactions += reaction;
	}

	public override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return true
	}

	public override propagateChange(EChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		LOGGER.trace("Call relevant reactions");
		for (reaction : reactions) {
			LOGGER.debug("Calling reaction: " + reaction.class.simpleName + " with change: " + change);
			val executionState = new ReactionExecutionState(userInteracting, correspondenceModel, resourceAccess, this);
			reaction.applyEvent(change, executionState)
		}
	}

	override setUserInteracting(UserInteracting userInteracting) {
		super.setUserInteracting(userInteracting);
		reactions = newArrayList;
		setup();
	}

	protected abstract def void setup();

}
