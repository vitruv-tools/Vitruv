package tools.vitruvius.extensions.dslsruntime.response

import org.apache.log4j.Logger
import java.util.List
import tools.vitruvius.framework.util.command.EMFCommandBridge
import java.util.ArrayList
import tools.vitruvius.extensions.dslsruntime.response.IResponseRealization
import tools.vitruvius.framework.userinteraction.UserInteracting
import tools.vitruvius.extensions.dslsruntime.response.helper.Change2ResponseMap
import tools.vitruvius.framework.change.echange.EChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.change.processing.impl.AbstractEChangeProcessor
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand

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
	
	public override List<VitruviusRecordingCommand> transformChange(EChange event, CorrespondenceModel correspondenceModel) {
		val result = new ArrayList<VitruviusRecordingCommand>();
		val relevantResponses = this.changeToResponseMap.getResponses(event).filter[checkPrecondition(event)];
		LOGGER.debug("Call relevant responses");
		for (response : relevantResponses) {
			LOGGER.debug(response.toString());
			result.add(EMFCommandBridge
					.createVitruviusTransformationRecordingCommand([| response.applyEvent(event, correspondenceModel)]));
		}
		return result;
	}

	protected abstract def void setup();
	
}