package tools.vitruv.extensions.dslsruntime.response

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.response.IResponseRealization
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.extensions.dslsruntime.response.helper.Change2ResponseMap
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.impl.AbstractEChangeProcessor
import tools.vitruv.framework.util.command.TransformationResult

abstract class AbstractResponseExecutor extends AbstractEChangeProcessor {
	private final static val LOGGER = Logger.getLogger(AbstractResponseExecutor);

	private Change2ResponseMap changeToResponseMap;

	protected UserInteracting userInteracting;
	
	new (UserInteracting userInteracting) {
		super(userInteracting);
		this.changeToResponseMap = new Change2ResponseMap();
		this.userInteracting = userInteracting;
		this.setup();
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
		val propagationResult = new TransformationResult();
		val relevantResponses = event.relevantResponses;
		LOGGER.debug("Call relevant responses");
		for (response : relevantResponses) {
			LOGGER.debug(response.toString());
			val currentPropagationResult =response.applyEvent(event, correspondenceModel)
			propagationResult.integrateTransformationResult(currentPropagationResult);
		}
		return propagationResult;
	}
	
	protected abstract def void setup();
	
}