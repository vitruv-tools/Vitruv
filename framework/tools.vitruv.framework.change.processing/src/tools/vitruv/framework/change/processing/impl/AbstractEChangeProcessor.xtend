package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.change.processing.impl.AbstractChangeProcessor
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.echange.EChange
import org.apache.log4j.Logger
import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.processing.ChangeProcessorResult
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import tools.vitruv.framework.change.description.TransactionalChange

abstract class AbstractEChangeProcessor extends AbstractChangeProcessor {
	private final static val LOGGER = Logger.getLogger(AbstractEChangeProcessor);
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
	}
	
	override transformChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val commandList = new ArrayList<VitruviusRecordingCommand>();
		for (eChange : change.getEChanges) {
			LOGGER.debug('''Transforming eChange  «eChange» of change «change»''');
			commandList += transformChange(eChange, correspondenceModel);
		}
		
		return new ChangeProcessorResult(VitruviusChangeFactory.instance.createEmptyChange(change.getURI), commandList);
	}
	
	protected def List<VitruviusRecordingCommand> transformChange(EChange change, CorrespondenceModel correspondenceModel);
	
}
			