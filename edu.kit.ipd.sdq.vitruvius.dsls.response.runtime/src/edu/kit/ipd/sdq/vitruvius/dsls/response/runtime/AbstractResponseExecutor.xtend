package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import org.apache.log4j.Logger
import java.util.List
import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.IResponseRealization
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.Change2ResponseMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

abstract class AbstractResponseExecutor implements ResponseExecutor {
	private final static val LOGGER = Logger.getLogger(AbstractResponseExecutor);

	private Change2ResponseMap changeToResponseMap;

	protected UserInteracting userInteracting;
	
	new (UserInteracting userInteracting) {
		this.changeToResponseMap = new Change2ResponseMap();
		this.userInteracting = userInteracting;
		this.setup();
	}

	protected def void addResponse(Class<? extends EChange> eventType, IResponseRealization response) {
		this.changeToResponseMap.addResponse(eventType, response);
	}
	
	public override List<Command> generateCommandsForEvent(EChange event, CorrespondenceModel correspondenceInstance) {
		val result = new ArrayList<Command>();
		val relevantResponses = this.changeToResponseMap.getResponses(event).filter[checkPrecondition(event)];
		LOGGER.debug("Call relevant responses");
		for (response : relevantResponses) {
			LOGGER.debug(response.toString());
			result.add(EMFCommandBridge
					.createVitruviusTransformationRecordingCommand([| response.applyEvent(event, correspondenceInstance)]) as Command);
		}
		return result;
	}

	protected abstract def void setup();
	
}