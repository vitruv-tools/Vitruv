package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import org.apache.log4j.Logger
import java.util.List
import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.interfaces.IResponseRealization
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting

abstract class AbstractResponseExecutor  {
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
	
	public def List<Command> generateCommandsForEvent(EChange event, Blackboard blackboard) {
		return handleEvent(event, blackboard);
	}

	protected def List<Command> callRelevantResponses(EChange event, Blackboard blackboard) {
		val result = new ArrayList<Command>();
		val relevantResponses = this.changeToResponseMap.getResponses(event);
		LOGGER.debug("call relevant responses");
		for (response : relevantResponses) {
			LOGGER.debug(response.toString());
			result.add(EMFCommandBridge
					.createVitruviusTransformationRecordingCommand([| response.applyEvent(event, blackboard)]) as Command);
		}
		return result;
	}

	protected def List<Command> handleEvent(EChange event, Blackboard blackboard) {
		return this.callRelevantResponses(event, blackboard);
	}

	protected def void setup();
}