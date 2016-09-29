package tools.vitruv.extensions.dslsruntime.response

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.response.IResponseRealization
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.extensions.dslsruntime.response.helper.Change2ResponseMap
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import tools.vitruv.framework.util.command.ChangePropagationResult

abstract class AbstractResponseExecutor extends AbstractEChangePropagationSpecification {
	private final static val LOGGER = Logger.getLogger(AbstractResponseExecutor);

	private Change2ResponseMap changeToResponseMap;
	private final MetamodelPair metamodelPair;
	
	new (UserInteracting userInteracting, MetamodelPair metamodelPair) {
		super(userInteracting);
		this.changeToResponseMap = new Change2ResponseMap();
		this.metamodelPair = metamodelPair;
		this.setup();
	}
	
	override getMetamodelPair() {
		return metamodelPair;
	}

	protected def void addResponse(Class<? extends EChange> eventType, IResponseRealization response) {
		this.changeToResponseMap.addResponse(eventType, response);
	}
	
	private def Iterable<IResponseRealization> getRelevantResponses(EChange change) {
		return this.changeToResponseMap.getResponses(change).filter[checkPrecondition(change)];
	}
	
	public override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return !change.relevantResponses.isEmpty
	}
	
	public override propagateChange(EChange event, CorrespondenceModel correspondenceModel) {
		val propagationResult = new ChangePropagationResult();
		val relevantResponses = event.relevantResponses;
		LOGGER.debug("Call relevant responses");
		for (response : relevantResponses) {
			LOGGER.debug(response.toString());
			val currentPropagationResult =response.applyEvent(event, correspondenceModel)
			propagationResult.integrateResult(currentPropagationResult);
		}
		return propagationResult;
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		super.setUserInteracting(userInteracting);
		changeToResponseMap = new Change2ResponseMap();
		setup();
	}
	
	protected abstract def void setup();
	
}