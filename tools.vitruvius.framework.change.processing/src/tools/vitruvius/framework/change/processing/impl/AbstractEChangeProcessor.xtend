package tools.vitruvius.framework.change.processing.impl

import tools.vitruvius.framework.change.processing.impl.AbstractChangeProcessor
import tools.vitruvius.framework.change.description.ConcreteChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.change.echange.EChange
import org.apache.log4j.Logger
import java.util.List
import java.util.ArrayList
import tools.vitruvius.framework.change.description.VitruviusChangeFactory
import tools.vitruvius.framework.userinteraction.UserInteracting
import tools.vitruvius.framework.change.processing.ChangeProcessorResult
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand

abstract class AbstractEChangeProcessor extends AbstractChangeProcessor {
	private final static val LOGGER = Logger.getLogger(AbstractEChangeProcessor);
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
	}
	
	override transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel) {
		val commandList = new ArrayList<VitruviusRecordingCommand>();
		for (eChange : change.getEChanges) {
			LOGGER.debug('''Transforming eChange  «eChange» of change «change»''');
			commandList += transformChange(eChange, correspondenceModel);
		}
		
		return new ChangeProcessorResult(VitruviusChangeFactory.instance.createEmptyChange(change.getURI), commandList);
	}
	
	protected def List<VitruviusRecordingCommand> transformChange(EChange change, CorrespondenceModel correspondenceModel);
	
}
			