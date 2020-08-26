package tools.vitruv.extensions.dslsruntime.reactions

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.util.command.ResourceAccess
import java.util.List

abstract class AbstractReactionsExecutor extends AbstractEChangePropagationSpecification {
	private final static val LOGGER = Logger.getLogger(AbstractReactionsExecutor);

	private val RoutinesFacadesProvider routinesFacadesProvider;
	private List<IReactionRealization> reactions;

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain);
		this.reactions = newArrayList;
		this.routinesFacadesProvider = this.createRoutinesFacadesProvider();
		this.setup();
	}

	protected def getRoutinesFacadesProvider() {
		return routinesFacadesProvider;
	}

	protected def void addReaction(IReactionRealization reaction) {
		this.reactions += reaction;
	}

	public override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return true
	}

	public override propagateChange(EChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		LOGGER.trace("Call relevant reactions from " + sourceDomain.name + " to " + targetDomain.name);
		for (reaction : reactions) {
			LOGGER.trace("Calling reaction: " + reaction.class.simpleName + " with change: " + change);
			val executionState = new ReactionExecutionState(userInteractor, correspondenceModel, resourceAccess, this);
			reaction.applyEvent(change, executionState)
		}
	}

	override setUserInteractor(UserInteractor userInteractor) {
		super.setUserInteractor(userInteractor);
		reactions = newArrayList;
		setup();
	}

	protected abstract def RoutinesFacadesProvider createRoutinesFacadesProvider();

	protected abstract def void setup();

}
